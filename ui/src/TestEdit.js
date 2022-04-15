import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavBar';

class TestEdit extends Component {

    emptyItem = {
        testName: "",
        status: "undefined"
    }

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new'){
            const test = await (await fetch(`/tests/${this.props.match.params.id}`)).json();
            this.setState({item: test});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const testName = target.name;
        let item = {...this.state.item};
        item[testName] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch('/tests' + (item.id ? '/' + item.id: ''), {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/tests');
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit Test' : 'Add Test'}</h2>;
        
        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="testName">Test Name</Label>
                        <Input type="text" testName="test" id="test" value={item.testName || ''}
                            onChange={this.handleChange} autoComplete="testName"/>
                    </FormGroup>
                    <FormGroup>
                    <Label for="status">status</Label>
                        <Input type="text" status="status" id="status" value={item.status || ''}
                            onChange={this.handleChange} autoComplete="status"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color='primary' type='submit'>Save</Button>{' '}
                        <Button color='secondary' tag={Link} to="/tests">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }

}
export default withRouter(TestEdit);