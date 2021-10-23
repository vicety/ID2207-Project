package handler

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func Init(r *gin.Engine) *gin.Engine {
	r.GET("/", func(context *gin.Context) {
		context.JSON(http.StatusOK, "okk")
	})
	r.POST("/login", Login)
	r.POST("/reqFormList", ReqFormList)
	r.POST("/reqForm1", ReqEvent)
	r.POST("/reqForm2", ReqEvent)
	r.POST("/reqForm3", ReqEvent)
	r.POST("/reqForm4", ReqEvent)
	r.POST("/modifyForm", ModifyForm)
	r.POST("/confirmForm", ConfirmForm)
	r.POST("/initForm", InitForm)

	return r
}
