'use strict';

const ce = React.createElement;

class ShowHideComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = { showing: true };
  }

  render() {
    if (this.state.showing) {
      return ce('div', null,
        ce('button', { onClick: () => this.setState({showing : false})  }, 'Hide view'),
        ce('h1', null, 'Hello, React!'),
      );
    } else {
      return ce('div', null,
        ce('button', { onClick: () => this.setState({showing : true})  }, 'Show view'),
        // ce('h1', null, 'Hello, React!'),
      );
    }
  }
}

// const ajaxCounter = document.getElementById('ajax-counter');
// const ajaxCounterButton = document.getElementById('ajax-counter-button');
// ajaxCounterButton.onclick = () => {
//     fetch("/counter/increment", {
//       method: 'POST'
//     })
//     .then((response) => response.text())
//     .then((newCounterValue) => {
//       ajaxCounter.innerHTML = newCounterValue;
//     });
// }

class CounterComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = { counter: 0 };
  }

  onClickHandler(e) {
    fetch("/counter/increment", {
      method: 'POST'
    })
    .then((response) => response.text())
    .then((newCounterValue) => {
      this.setState({ counter: parseInt(newCounterValue)});
    });
  }

  render() {
    return ce('div', null,
      ce('h1', null, 'The counter\'s value is ' + this.state.counter),
      ce('button', { onClick: e => this.onClickHandler(e) }, 'Increment counter'),
    );
  }
}

ReactDOM.render(
  ce(CounterComponent),
  document.getElementById('react-root'),
);