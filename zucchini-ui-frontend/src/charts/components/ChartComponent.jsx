import React from 'react';
import PropTypes from 'prop-types';
import shallowEqual from 'recompose/shallowEqual';
import Chart from 'chart.js';


export default class ChartComponent extends React.PureComponent {

  static propTypes = {
    type: PropTypes.string.isRequired,
    data: PropTypes.object,
    options: PropTypes.object,
  };

  static defaultProps = {
    data: {},
    options: {},
  };

  constructor(props) {
    super(props);

    this.canvasElement = null;
    this.chart = null;
  }

  componentDidMount() {
    this.chart = new Chart(this.canvasElement, {
      ...this.props,
    });
  }

  componentDidUpdate({ options: previousOptions, type: previousType }) {
    const { options, type, data } = this.props;
    const shouldRecreate = !shallowEqual(options, previousOptions) || type !== previousType;

    if (shouldRecreate) {
      this.chart.destroy();
      this.chart = new Chart(this.canvasElement, {
        ...this.props,
        options: {
          ...options,
          responsive: true,
        },
      });
    } else {
      this.chart.data = data;
      this.chart.update();
    }
  }

  componentWillUnmount() {
    this.chart.destroy();
  }

  setCanvasElement = element => {
    this.canvasElement = element;
  }

  render() {
    return (
      <div style={{ position: 'relative' }}>
        <canvas ref={this.setCanvasElement} />
      </div>
    );
  }

}
