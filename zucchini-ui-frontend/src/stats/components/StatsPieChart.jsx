import PropTypes from 'prop-types';
import React from 'react';

import ChartComponent from '../../charts/components/ChartComponent';


export default class StatsPieChart extends React.PureComponent {

  static propTypes = {
    stats: PropTypes.object.isRequired,
    total: PropTypes.number.isRequired,
  };

  constructor(props) {
    super(props);

    this.state = {
      data: this.computeChartData(props.stats),
      options: {
        animation: {
          duration: 0,
        },
        circumference: Math.PI * 2 * this.computeCompletion(props.stats.count, props.total),
        legend: {
          display: false,
        },
      },
    };
  }

  componentWillReceiveProps(nextProps) {
    this.setState(prevState => {
      return {
        data: this.computeChartData(nextProps.stats),
        options: {
          ...prevState.options,
          circumference: Math.PI * 2 * this.computeCompletion(nextProps.stats.count, nextProps.total),
        },
      };
    });
  }

  computeChartData(stats) {
    if (!stats) {
      return {};
    }

    return {
      datasets: [{
        data: [
          stats.passed,
          stats.pending,
          stats.failed,
          stats.notRun,
        ],
        backgroundColor: [
          '#5cb85c',
          '#f0ad4e',
          '#d9534f',
          '#777',
        ],
      }],
      labels: [
        'Succès',
        'En attente',
        'Échec',
        'Non joué',
      ],
    };
  }

  computeCompletion(count, total) {
    if (total > 0) {
      return count / total;
    }
    return 0;
  }

  render() {
    return (
      <ChartComponent
        type='doughnut'
        data={this.state.data}
        options={this.state.options} />
    );
  }

}
