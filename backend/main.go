package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"kth.se/id2207-project/config"
	"kth.se/id2207-project/handler"
	"kth.se/id2207-project/model"
)

const (
	Port = 8080
)

func main() {
	if err := config.InitDB(); err != nil {
		fmt.Printf("Failed to init db, err: %v\n", err)
		return
	}

	if err := model.InitData(); err != nil {
		fmt.Printf("Failed to init data, err: %v\n", err)
		return
	}

	gin.SetMode(gin.DebugMode)
	engine := gin.New()

	engine = handler.Init(engine)
	if err := engine.Run(fmt.Sprintf(":%d", Port)); err != nil {
		fmt.Printf("Failed to start server on port %d, err: %v\n", Port, err)
		return
	}
}
