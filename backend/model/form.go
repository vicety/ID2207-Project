package model

import (
	"encoding/json"
	"github.com/jinzhu/gorm"
)

type Form struct {
	FormId          string `json:"formId"`
	FormType        string `json:"formType"`
	ResponsibleUser string `json:"-"`
	Timestamp       int    `json:"-"`

	// EventForm
	ClientName     string `json:"clientName"`
	From           string `json:"from"`
	To             string `json:"to"`
	Number         string `json:"number"`
	Prefer         string `json:"prefer"`
	ExpectedBudget string `json:"expectedBudget"`
	Status         string `json:"status"`

	// RecruitmentForm
	ContractType     string `json:"contractType"`
	Department       string `json:"department"`
	YearOfExperience string `json:"yearOfExperience"`
	JobTitle         string `json:"jobTitle"`
	JobDescription   string `json:"jobDescription"`

	// FinancialForm
	// Department string `json:"department"`
	Amount string `json:"amount"`
	Reason string `json:"reason"`

	// TaskForm
	Assignto        string `json:"assignto"`
	Priority        string `json:"priority"`
	TaskDescription string `json:"taskDescription"`
	Sender          string `json:"sender"`
	Comment         string `json:"comment"`
}

func (f Form) ToEventForm() *EventForm {
	js, err := json.Marshal(f)
	if err != nil {
		println(err)
		return nil
	}
	eventForm := EventForm{}
	err = json.Unmarshal(js, &eventForm)
	if err != nil {
		println(err)
		return nil
	}
	return &eventForm
}

func (f Form) ToRecruitmentForm() *RecruitmentForm {
	js, err := json.Marshal(f)
	if err != nil {
		println(err)
		return nil
	}
	eventForm := RecruitmentForm{}
	err = json.Unmarshal(js, &eventForm)
	if err != nil {
		println(err)
		return nil
	}
	return &eventForm
}

func (f Form) ToFinancialForm() *FinancialForm {
	js, err := json.Marshal(f)
	if err != nil {
		println(err)
		return nil
	}
	eventForm := FinancialForm{}
	err = json.Unmarshal(js, &eventForm)
	if err != nil {
		println(err)
		return nil
	}
	return &eventForm
}

func (f Form) ToTaskForm() *TaskForm {
	js, err := json.Marshal(f)
	if err != nil {
		println(err)
		return nil
	}
	eventForm := TaskForm{}
	err = json.Unmarshal(js, &eventForm)
	if err != nil {
		println(err)
		return nil
	}
	return &eventForm
}

func (Form) TableName() string {
	return "form"
}

func CreateForm(db *gorm.DB, form *Form) error {
	return db.Create(form).Error
}

func GetForm(db *gorm.DB, formId string) (*Form, error) {
	form := &Form{}
	result := db.Model(&Form{}).Where("form_id = ?", formId).First(form)
	return form, result.Error
}

func UpdateForm(db *gorm.DB, form *Form) error {
	return db.Model(&Form{}).Where(&Form{FormId: form.FormId}).Update(&form).Error
}

func GetFormByUser(db *gorm.DB, userName string) ([]Form, error) {
	var formList []Form
	result := db.Model(&Form{}).Where("responsible_user = ?", userName).Find(&formList)
	return formList, result.Error
}
