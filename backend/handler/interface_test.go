package handler

import (
	"github.com/stretchr/testify/assert"
	"kth.se/id2207-project/config"
	"kth.se/id2207-project/errors"
	"kth.se/id2207-project/model"
	"testing"
)

func TestLogin(t *testing.T) {
	var err error
	assert.Nil(t, config.InitDB())

	model.InitData()

	_, err = handleLogin(&LoginRequest{User: model.User{
		UserName: "ss",
		Password: "dd",
	}})
	assert.Equal(t, errors.InvalidUser, err)

	_, err = handleLogin(&LoginRequest{User: model.User{
		UserName: "FinancialManager",
		Password: "FM",
	}})
	assert.Equal(t, nil, err)
}
