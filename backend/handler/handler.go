package handler

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/jinzhu/gorm"
	"kth.se/id2207-project/config"
	"kth.se/id2207-project/errors"
	"kth.se/id2207-project/model"
	"math/rand"
	"net/http"
	"strconv"
)

type LoginRequest struct {
	model.User
}

type LoginResponse struct {
	Status    string `json:"status"` // ok or Invalid User
	Role      string `json:"role"`
	SessionId string `json:"sessionId"`
}

type StatusResponse struct {
	Status string `json:"status"`
}

type ReqFormListRequest struct {
	SessionId
}

type InitFormRequest struct {
	FormType string `json:"formType"`
}

type ReqFormListResponseItem struct {
	FormId   string `json:"formId"`
	FormType string `json:"formType"`
}

type ReqEventRequest struct {
	SessionId
	FormId string `json:"formId"`
}

type ConfirmFormRequest struct {
	FormId string `json:"formId"`
}

type ModifyFormRequest struct {
	model.Form
}

type SessionId struct {
	SessionId string `json:"sessionId"`
}

func Login(c *gin.Context) {
	request := &LoginRequest{}
	if err := c.ShouldBindJSON(request); err != nil {
		fmt.Printf("Failed to parse login request, err: %v\n", err)
		c.Status(http.StatusBadRequest)
		return
	}

	resp, err := handleLogin(request)
	if err != nil && err != errors.InvalidUser {
		fmt.Printf("Failed to handle login request, err: %v\n", err)
		c.Status(http.StatusBadRequest)
		return
	} else if err != nil && err == errors.InvalidUser {
		c.JSON(http.StatusOK, &LoginResponse{Status: errors.InvalidUser.Error()})
		return
	}

	c.JSON(http.StatusOK, resp)
}

func ReqFormList(c *gin.Context) {
	request := &ReqFormListRequest{}
	if err := c.ShouldBindJSON(request); err != nil {
		fmt.Printf("Failed to parse ReqFormList request, err: %v\n", err)
		c.Status(http.StatusBadRequest)
		return
	}

	ans, err := handleReqFormList(request)
	if err != nil {
		c.Status(http.StatusBadRequest)
		return
	}

	c.JSON(http.StatusOK, ans)
}

func handleReqFormList(request *ReqFormListRequest) ([]ReqFormListResponseItem, error) {
	user, err := model.GetUserFromSid(config.DB, request.SessionId.SessionId)
	if err == gorm.ErrRecordNotFound {
		return nil, errors.UserNotExist
	} else if err != nil {
		fmt.Printf("Failed to lookup user from sid, err: %v\n", err)
		return nil, err
	}

	resp, err := model.GetFormByUser(config.DB, user.UserName)
	if err != nil {
		fmt.Printf("Failed to get form by user, err: %v\n", err)
		return nil, err
	}

	ans := make([]ReqFormListResponseItem, 0)
	for _, r := range resp {
		ans = append(ans, ReqFormListResponseItem{
			FormId:   r.FormId,
			FormType: r.FormType,
		})
	}

	return ans, nil
}

func ReqEvent(c *gin.Context) {
	request := &ReqEventRequest{}
	if err := c.ShouldBindJSON(request); err != nil {
		fmt.Printf("Failed to parse ReqEventRequest request, err: %v\n", err)
		c.Status(http.StatusBadRequest)
		return
	}

	form, err := handleReqEvent(request)
	if err != nil {
		c.Status(http.StatusBadRequest)
		return
	}

	c.JSON(http.StatusOK, form)
}

func handleReqEvent(request *ReqEventRequest) (interface{}, error) {
	form, err := model.GetForm(config.DB, request.FormId)
	if err == gorm.ErrRecordNotFound {
		return nil, errors.UserNotExist
	}
	if err != nil {
		fmt.Printf("Failed to get form by formId, err: %v\n", err)
		return nil, err
	}

	if form.FormType == "EVENT" {
		return form.ToEventForm(), nil
	} else if form.FormType == "TASK" {
		return form.ToTaskForm(), nil
	} else if form.FormType == "HR" {
		return form.ToRecruitmentForm(), nil
	} else {
		return form.ToFinancialForm(), nil
	}
}

func ModifyForm(c *gin.Context) {
	request := &ModifyFormRequest{}
	if err := c.ShouldBindJSON(request); err != nil {
		fmt.Printf("Failed to parse ReqEventRequest request, err: %v\n", err)
		c.Status(http.StatusBadRequest)
		return
	}

	// ensure form exists
	_, err := model.GetForm(config.DB, request.FormId)
	if err == gorm.ErrRecordNotFound {
		c.JSON(http.StatusOK, errors.UserNotExist.Error())
		return
	}
	if err != nil {
		fmt.Printf("Failed to get form by formId, err: %v\n", err)
		c.Status(http.StatusBadRequest)
		return
	}

	if err = model.UpdateForm(config.DB, &request.Form); err != nil {
		fmt.Printf("Failed to update form, err: %v\n", err)
		c.JSON(http.StatusInternalServerError, err.Error())
		return
	}

	c.JSON(http.StatusOK, StatusResponse{Status: "ok"})
}

func ConfirmForm(c *gin.Context) {
	request := &ConfirmFormRequest{}
	if err := c.ShouldBindJSON(request); err != nil {
		fmt.Printf("Failed to parse ReqEventRequest request, err: %v\n", err)
		c.Status(http.StatusBadRequest)
		return
	}

	if err := handleConfirmForm(request); err != nil {
		c.Status(http.StatusBadRequest)
		return
	}

	c.JSON(http.StatusOK, StatusResponse{Status: "ok"})
}

func handleConfirmForm(request *ConfirmFormRequest) error {
	form, err := model.GetForm(config.DB, request.FormId)
	if err != nil {
		fmt.Printf("Failed to get form by formId, err: %v\n", err)
		return err
	}

	if form.ResponsibleUser == "zzz" {
		return nil
	}

	// get next type
	nextType := getNextResponsibleUserType(form.FormType, form.Timestamp)

	form.Timestamp++
	if nextType == "" {
		form.ResponsibleUser = "zzz" // any not used user here
	} else {
		user, err := model.GetUserByUserType(config.DB, nextType)
		if err != nil {
			fmt.Printf("Failed to get user by userType %s, err: %v\n", nextType, err)
			return err
		}
		form.ResponsibleUser = user.UserName
	}

	if err = model.UpdateForm(config.DB, form); err != nil {
		fmt.Printf("Failed to update form, err: %v\n", err)
		return err
	}

	return nil
}

func InitForm(c *gin.Context) {
	request := &InitFormRequest{}
	if err := c.ShouldBindJSON(request); err != nil {
		fmt.Printf("Failed to parse CreateForm request, err: %v\n", err)
		c.Status(http.StatusBadRequest)
		return
	}

	formId := strconv.Itoa(rand.Int())
	formType := request.FormType
	form := model.Form{FormId: formId, FormType: formType}

	var uType string
	if formType == "EVENT" {
		uType = "CS"
	} else if formType == "TASK" {
		uType = "SM/PM"
	} else if formType == "HR" {
		uType = "SM/PM"
	} else {
		uType = "SM/PM"
	}

	user, err := model.GetUserByUserType(config.DB, uType)
	if err != nil {
		println(err)
		c.Status(http.StatusBadRequest)
		return
	}
	form.ResponsibleUser = user.UserName
	if err = model.CreateForm(config.DB, &form); err != nil {
		println(err)
		c.Status(http.StatusBadRequest)
		return
	}

	if form.FormType == "EVENT" {
		c.JSON(http.StatusOK, form.ToEventForm())
		return
	} else if form.FormType == "TASK" {
		c.JSON(http.StatusOK, form.ToTaskForm())
		return
	} else if form.FormType == "HR" {
		c.JSON(http.StatusOK, form.ToRecruitmentForm())
		return
	} else {
		c.JSON(http.StatusOK, form.ToFinancialForm())
		return
	}
}

func getNextResponsibleUserType(formType string, timestamp int) string {
	if formType == "EVENT" {
		t2t := []string{"SCS", "FM", "AM", "SCS", ""}
		return t2t[timestamp]
	} else if formType == "TASK" {
		t2t := []string{"SUB", "SM/PM", ""}
		return t2t[timestamp]
	} else if formType == "HR" {
		t2t := []string{"HR", "SM/PM", ""}
		return t2t[timestamp]
	} else {
		// FINANCIAL
		t2t := []string{"FM", "SM/PM", ""}
		return t2t[timestamp]
	}
}

func handleLogin(r *LoginRequest) (*LoginResponse, error) {
	user, err := model.GetUser(config.DB, r.UserName)
	if err == gorm.ErrRecordNotFound {
		return nil, errors.InvalidUser
	} else if err != nil {
		return nil, errors.InternalError
	}

	if user.Password != r.Password {
		return nil, errors.InvalidUser
	}

	// uuid.New().String()

	return &LoginResponse{Status: "ok", Role: user.Role, SessionId: model.Md5(r.UserName)}, nil
}
