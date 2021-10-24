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
		UserName: "user not exist",
		Password: "pwd not exist",
	}})
	assert.Equal(t, errors.InvalidUser, err)

	_, err = handleLogin(&LoginRequest{User: model.User{
		UserName: "FinancialManager",
		Password: "FM",
	}})
	assert.Equal(t, nil, err)
}

func TestReqFormList(t *testing.T) {
	var err error
	assert.Nil(t, config.InitDB())

	model.InitData()

	_, err = handleReqFormList(&ReqFormListRequest{
		SessionId: SessionId{SessionId: "sid not exist"},
	})
	assert.Equal(t, errors.UserNotExist, err)

	_, err = handleReqFormList(&ReqFormListRequest{
		SessionId: SessionId{SessionId: "bb68cccaacfeb621c4d539014b47a3f8"},
	})
	assert.Nil(t, err)
}

func TestReqEvent(t *testing.T) {
	var err error
	assert.Nil(t, config.InitDB())

	model.InitData()

	_, err = handleReqEvent(&ReqEventRequest{
		FormId: "not exist",
	})
	assert.NotNil(t, err)

	form, err := handleReqEvent(&ReqEventRequest{
		FormId: "123",
	})
	assert.Nil(t, err)
	assert.Equal(t, "EVENT", form.(*model.EventForm).FormType)
}

func TestConfirmForm(t *testing.T) {
	var err error
	assert.Nil(t, config.InitDB())

	model.InitData()

	err = handleConfirmForm(&ConfirmFormRequest{FormId: "123"})
	assert.Nil(t, err)

	// SCS should be able to see
	forms, err := handleReqFormList(&ReqFormListRequest{SessionId: SessionId{SessionId: "a54d7486b9caf6bb94daa069cdc8ca07"}})
	assert.Nil(t, err)
	assert.Equal(t, 1, len(forms))

	err = handleConfirmForm(&ConfirmFormRequest{FormId: "123"})
	assert.Nil(t, err)

	// SCS should not be able to see
	forms, err = handleReqFormList(&ReqFormListRequest{SessionId: SessionId{SessionId: "a54d7486b9caf6bb94daa069cdc8ca07"}})
	assert.Nil(t, err)
	assert.Equal(t, 0, len(forms))
}
