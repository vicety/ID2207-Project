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

	return r
}
