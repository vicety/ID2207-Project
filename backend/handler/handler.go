package handler

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/jinzhu/gorm"
	"kth.se/id2207-project/config"
	"kth.se/id2207-project/errors"
	"kth.se/id2207-project/model"
	"net/http"
)

type LoginRequest struct {
	model.User
}

type LoginResponse struct {
	Status string `json:"status"` // ok or Invalid User
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

	return &LoginResponse{Status: "ok"}, nil
}
