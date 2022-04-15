import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavBar';
import { Link } from 'react-router-dom';

class TestList extends Component {

    constructor(props) {
        super(props);
        this.state = {tests: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('/tests')
        .then(response => response.json())
        .then(data => this.setState({tests: data}));
    }

    async remove(id) {
        await fetch('/tests/${id}', {
            method: 'DELETE',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedTests = [...this.state.tests].filter(i => i.id !== id);
            this.setState({tests: updatedTests});
        });
    }

  render() {
      const {tests, isLoading} = this.state;

      if(isLoading) {
          return <p>Loading...</p>;
      }

      const testList = tests.map(test => {
          return <tr key={test.testName}>
              <td style={{whiteSpace: 'nowrap'}}>{test.testName}</td>
              <td>{test.status}</td>
              <td>
                  <ButtonGroup>
                      <Button size="sm" color="primary" tag={Link} to={"/tests/" + test.id}>Edit</Button>
                      <Button size="sm" color="danger" onClick={() => this.remove(test.id)}>Delete</Button>
                  </ButtonGroup>
              </td>
          </tr>
      })

      return (
          <div>
          <AppNavbar/>
          <Container fluid>
              <div className="float-right">
                  <Button color="success" tag={Link} to="/test/new">Add Test</Button>
              </div>
              <h3>Tests</h3>
              <Table className="mt-4">
                  <thead>
                  <tr>
                      <th width="30%">Name</th>
                      <th width="30%">Status</th>
                      <th width="40%">Actions</th>
                  </tr>
                  </thead>
                  <tbody>
                  {testList}
                  </tbody>
              </Table>
          </Container>
      </div>
      );
  }

}
export default TestList;