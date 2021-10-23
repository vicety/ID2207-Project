package model

type EventForm struct {
	FormId         string `json:"formId"`
	FormType       string `json:"formType"`
	ClientName     string `json:"clientName"`
	From           string `json:"from"`
	To             string `json:"to"`
	Number         string `json:"number"`
	Prefer         string `json:"prefer"`
	ExpectedBudget string `json:"expectedBudget"`
	Status         string `json:"status"`
}

type RecruitmentForm struct {
	FormId   string `json:"formId"`
	FormType string `json:"formType"`

	ContractType     string `json:"contractType"`
	Department       string `json:"department"`
	YearOfExperience string `json:"yearOfExperience"`
	JobTitle         string `json:"jobTitle"`
	JobDescription   string `json:"jobDescription"`
}

type FinancialForm struct {
	FormId   string `json:"formId"`
	FormType string `json:"formType"`

	Department string `json:"department"`
	Amount     string `json:"amount"`
	Reason     string `json:"reason"`
}

type TaskForm struct {
	FormId   string `json:"formId"`
	FormType string `json:"formType"`

	Assignto        string `json:"assignto"`
	Priority        string `json:"priority"`
	TaskDescription string `json:"taskDescription"`
	Sender          string `json:"sender"`
	Comment         string `json:"comment"`
}
