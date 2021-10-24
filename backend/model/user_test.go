package model

import (
	"github.com/stretchr/testify/assert"
	"kth.se/id2207-project/config"
	"testing"
)

func TestGetUser(t *testing.T) {
	var err error
	assert.Nil(t, config.InitDB())

	InitData()

	err = CreateUser(config.DB, &User{
		UserName: "uname",
		Password: "pwd",
	})
	assert.Nil(t, err)

	_, err = GetUser(config.DB, "user not exist")
	//assert.Equal(t, errors.InvalidUser(), err)
	assert.NotNil(t, err)

	user, err := GetUser(config.DB, "FinancialManager")
	assert.Nil(t, err)

	assert.Equal(t, "FM", user.Password)
}
