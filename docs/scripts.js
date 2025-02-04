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
                        text: 'Date'
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

// Function to fetch and parse CSV data
async function loadMetricsData() {
    try {
        const response = await fetch('mock-metrics.csv');
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const csvText = await response.text();
        console.log('Fetched CSV:', csvText); // Debug log

        return new Promise((resolve, reject) => {
            Papa.parse(csvText, {
                complete: (results) => {
                    console.log('Parsed results:', results); // Debug log
                    const data = processMetricsData(results.data);
                    resolve(data);
                },
                error: (error) => {
                    console.error('Papa Parse error:', error); // Debug log
                    reject(error);
                },
                header: true,
                skipEmptyLines: true,
                dynamicTyping: true // Automatically convert numbers
            });
        });
    } catch (error) {
        console.error('Error loading metrics:', error);
        throw error;
    }
}

// Process the raw CSV data into chart format
function processMetricsData(rawData) {
    console.log('Processing data:', rawData); // Debug log
    return {
        labels: rawData.map(row => row.date),
        datasets: [{
            label: 'Number of Cycles',
            data: rawData.map(row => row.cycles),
            borderColor: '#4169E1',
            backgroundColor: 'rgba(65, 105, 225, 0.2)',
            tension: 0.4
        }]
    };
}

// Initialize and update chart
async function initializeChart() {
    try {
        // Create initial empty chart
        const ctx = document.getElementById('cyclesOverTimeChart');
        const chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: [],
                datasets: [{
                    label: 'Number of Cycles',
                    data: [],
                    borderColor: '#4169E1',
                    backgroundColor: 'rgba(65, 105, 225, 0.2)',
                    tension: 0.4
                }]
            },
            options: chartConfigs.cyclesOverTime.options
        });

        // Load and update with real data
        const metricsData = await loadMetricsData();
        chart.data = metricsData;
        chart.update();

        console.log('Chart updated with data:', metricsData); // Debug log
    } catch (error) {
        console.error('Error initializing chart:', error);
        // Show error in UI
        const errorMessage = document.createElement('div');
        errorMessage.style.color = 'red';
        errorMessage.style.padding = '20px';
        errorMessage.textContent = 'Error loading data: ' + error.message;
        document.getElementById('cyclesOverTimeChart').parentNode.appendChild(errorMessage);
    }
}

// Initialize charts when document is loaded
document.addEventListener('DOMContentLoaded', () => {
    console.log('Document loaded, initializing chart...');
    initializeChart();
});