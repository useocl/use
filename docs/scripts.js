// Chart configuration and data
const chartConfigs = {
    cyclesOverTime: {
        type: 'line',
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Cyclic Dependencies'
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Number of Cycles'
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Time Period'
                    }
                }
            }
        }
    },
    complexityMetrics: {
        type: 'bar',
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Complexity Metrics'
                }
            }
        }
    },
    trendAnalysis: {
        type: 'line',
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Trend Analysis'
                }
            }
        }
    },
    comparativeMetrics: {
        type: 'radar',
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Comparative Metrics'
                }
            }
        }
    }
};

// Sample data - replace with real data later
const sampleData = {
    cyclesOverTime: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
        datasets: [{
            label: 'Number of Cycles',
            data: [12, 19, 3, 5, 2, 3],
            borderColor: '#3498db',
            backgroundColor: 'rgba(52, 152, 219, 0.2)',
            tension: 0.4
        }]
    },
    complexityMetrics: {
        labels: ['Metric A', 'Metric B', 'Metric C', 'Metric D'],
        datasets: [{
            label: 'Complexity Score',
            data: [65, 59, 80, 81],
            backgroundColor: 'rgba(46, 204, 113, 0.2)',
            borderColor: '#2ecc71',
            borderWidth: 1
        }]
    },
    trendAnalysis: {
        labels: ['Week 1', 'Week 2', 'Week 3', 'Week 4', 'Week 5'],
        datasets: [{
            label: 'Trend',
            data: [10, 15, 13, 17, 20],
            borderColor: '#e74c3c',
            backgroundColor: 'rgba(231, 76, 60, 0.2)',
            tension: 0.4
        }]
    },
    comparativeMetrics: {
        labels: ['Metric 1', 'Metric 2', 'Metric 3', 'Metric 4', 'Metric 5'],
        datasets: [{
            label: 'Current',
            data: [65, 59, 90, 81, 56],
            backgroundColor: 'rgba(52, 152, 219, 0.2)',
            borderColor: '#3498db'
        }, {
            label: 'Previous',
            data: [28, 48, 40, 19, 96],
            backgroundColor: 'rgba(231, 76, 60, 0.2)',
            borderColor: '#e74c3c'
        }]
    }
};

// Initialize charts when document is loaded
document.addEventListener('DOMContentLoaded', () => {
    // Initialize all charts
    const charts = Object.keys(chartConfigs).map(id => {
        const ctx = document.getElementById(`${id}Chart`);
        return new Chart(ctx, {
            type: chartConfigs[id].type,
            data: sampleData[id],
            options: chartConfigs[id].options
        });
    });
});