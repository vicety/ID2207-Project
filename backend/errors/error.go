package errors

import "fmt"

type Error struct {
	Msg string
}

var (
	InvalidUser   = fmt.Errorf("Invalid User")
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
