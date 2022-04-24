import React, { Component } from 'react';
import { Button, ButtonGroup } from 'reactstrap';

const headers = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  }

class TestStatus extends Component {

    sendData = (tests) => {
        this.props.callback(tests)
    }

    async markGreen(test) {
        await fetch(`/api/tests/${test.id}/status`, {
            method: 'PUT',
            body: "PASSING",
            headers: headers
        }).then(() => {
            test.status = "PASSING"
            this.sendData(test)
        });
      }
    
      async markRed(test) {
        await fetch(`/api/tests/${test.id}/status`, {
            method: 'PUT',
            body: "FAILING",
            headers: headers
        }).then(() => {
            test.status = "FAILING"
            this.sendData(test)
        });
      }

    render () {
        return (
            <ButtonGroup>
                <Button size='sm' color='success' 
                    onClick={() => this.markGreen(this.props.test)}>Pass</Button>
                <Button size='sm' color='danger' 
                    onClick={() => this.markRed(this.props.test)}>Fail</Button>
            </ButtonGroup>
        );

    }

}export default TestStatus;