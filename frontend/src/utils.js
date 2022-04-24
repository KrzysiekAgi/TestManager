import {toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css'

export function isTestNameValid(name, tests) {
    if (name.trim() == "") {
        toast.info("Cannot have a test with empty name", 
            {position: toast.POSITION.BOTTOM_RIGHT});
        return false;
    }
    else if (tests.filter(t => t.testName === name).length !== 0) {
        toast.info("There already is a test with that name", 
            {position: toast.POSITION.BOTTOM_RIGHT});
        return false;
    } else return true;
  }