package errors

import "fmt"

type Error struct {
	Msg string
}

var (
	UserNotExist  = fmt.Errorf("User Not Exist")
	InvalidUser   = fmt.Errorf("Invalid User")
	FormNotExist  = fmt.Errorf("Form Not Exist")
	InternalError = fmt.Errorf("Internal Error")
)

func (e *Error) Error() string {
	return e.Msg
}

//func InternalError() error {
//	return internalError
//}
//
//func InvalidUser() error {
//	return invalidUser
//	//return &Error{Msg: }
//}
