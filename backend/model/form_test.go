package model

import (
	"github.com/stretchr/testify/assert"
	"kth.se/id2207-project/config"
	"testing"
)

func TestGetForm(t *testing.T) {
	var err error
	assert.Nil(t, config.InitDB())

	InitData()

	err = CreateForm(config.DB, &Form{
		FormId:          "2345",
		FormType:        "EVENT",
		ResponsibleUser: "CustomerService",
		ClientName:      "xxx",
		From:            "2020-12-12",
		To:              "2021-09-09",
		Number:          "1",
		Prefer:          "DRINK",
		ExpectedBudget:  "3",
		Status:          "AD",
		Timestamp:       0,
	})
	assert.Nil(t, err)

	_, err = GetForm(config.DB, "form not exist")
	//assert.Equal(t, errors.InvalidUser(), err)
	assert.NotNil(t, err)

	_, err = GetForm(config.DB, "2345")
	assert.Nil(t, err)

	err = UpdateForm(config.DB, &Form{FormId: "2345", Status: "cccc"})
	assert.Nil(t, err)
	form, err := GetForm(config.DB, "2345")
	assert.Equal(t, form.Status, "cccc")

	_, err = GetFormByUser(config.DB, "CustomerService")
	assert.Nil(t, err)
}