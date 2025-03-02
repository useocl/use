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
                    text: 'Cyclic Dependencies without Tests'
                },
                tooltip: {
                    callbacks: {
                        // Customize the tooltip to show commit information
                        label: function(context) {
                            const label = context.dataset.label || '';
                            const value = context.parsed.y;
                            const commit = context.dataset.commitInfo[context.dataIndex];
                            return `${label}: ${value}\nCommit: ${commit}`;
                        }
                    }
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
                        text: 'Date & Time'
                    }
                }
            }
        }
    },
    complexityMetrics: {
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
                    text: 'Cyclic Dependencies with Tests'
                },
                tooltip: {
                    callbacks: {
                        // Customize the tooltip to show commit information
                        label: function(context) {
                            const label = context.dataset.label || '';
                            const value = context.parsed.y;
                            const commit = context.dataset.commitInfo[context.dataIndex];
                            return `${label}: ${value}\nCommit: ${commit}`;
                        }
                    }
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
                        text: 'Date & Time'
                    }
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
                    text: 'Layer Architecture Violations'
                },
                tooltip: {
                    callbacks: {
                        // Customize the tooltip to show commit information
                        label: function(context) {
                            const label = context.dataset.label || '';
                            const value = context.parsed.y;
                            const commit = context.dataset.commitInfo[context.dataIndex];
                            return `${label}: ${value}\nCommit: ${commit}`;
                        }
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    max: 1,
                    ticks: {
                        stepSize: 1
                    },
                    title: {
                        display: true,
                        text: 'Violations Present (0/1)'
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Date & Time'
                    }
                }
            }
        }
    },
    comparativeMetrics: {
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
                    text: 'Build Time'
                },
                tooltip: {
                    callbacks: {
                        // Customize the tooltip to show commit information
                        label: function(context) {
                            const label = context.dataset.label || '';
                            const value = context.parsed.y;
                            const commit = context.dataset.commitInfo[context.dataIndex];
                            return `${label}: ${value.toFixed(1)} seconds\nCommit: ${commit}`;
                        }
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Build Time (seconds)'
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Date & Time'
                    }
                }
            }
        }
    }
};

// Function to format date and time in the requested format (DD/MM/YY HH:MM)
function formatDateTime(dateStr, timeStr) {
    // Parse the date (assuming format is YYYY-MM-DD)
    const [year, month, day] = dateStr.split('-');

    // Extract only hours and minutes from time
    const timeParts = timeStr.split(':');
    const hours = timeParts[0];
    const minutes = timeParts[1];

    // Format as DD/MM/YY HH:MM
    return `${day}/${month}/${year.substring(2)} ${hours}:${minutes}`;
}

// Function to fetch and parse CSV data
async function fetchCSVData(filename) {
    try {
        const response = await fetch(filename);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const csvText = await response.text();
        console.log(`Fetched CSV from ${filename}:`, csvText); // Debug log

        return new Promise((resolve, reject) => {
            Papa.parse(csvText, {
                complete: (results) => {
                    console.log('Parsed results:', results); // Debug log
                    resolve(results.data);
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
        console.error(`Error loading metrics from ${filename}:`, error);
        throw error;
    }
}

// Generic function to process CSV data into chart format
function processChartData(rawData, valueKey) {
    console.log(`Processing data for ${valueKey}:`, rawData); // Debug log
    return {
        // Format dates for x-axis as DD/MM/YY HH:MM
        labels: rawData.map(row => formatDateTime(row.date, row.time)),
        datasets: [{
            label: getDatasetLabel(valueKey),
            data: rawData.map(row => row[valueKey]),
            borderColor: '#4169E1',
            backgroundColor: 'rgba(65, 105, 225, 0.2)',
            tension: 0.4,
            // Store commit information for each data point
            commitInfo: rawData.map(row => row.commit)
        }]
    };
}

// Helper function to get the appropriate dataset label based on the value key
function getDatasetLabel(valueKey) {
    switch(valueKey) {
        case 'cycles':
            return 'Number of Cycles';
        case 'violations':
            return 'Violations';
        case 'buildtime':
            return 'Build Time';
        default:
            return 'Value';
    }
}

// Initialize a single chart
async function initializeChart(chartId, configKey, csvFile, valueKey) {
    try {
        // Create initial empty chart
        const ctx = document.getElementById(chartId);
        const chart = new Chart(ctx, {
            type: chartConfigs[configKey].type,
            data: {
                labels: [],
                datasets: [{
                    label: getDatasetLabel(valueKey),
                    data: [],
                    borderColor: '#4169E1',
                    backgroundColor: 'rgba(65, 105, 225, 0.2)',
                    tension: 0.4,
                    commitInfo: [] // Initialize empty commit info array
                }]
            },
            options: chartConfigs[configKey].options
        });

        // Load and update with real data
        const rawData = await fetchCSVData(csvFile);
        const chartData = processChartData(rawData, valueKey);
        chart.data = chartData;
        chart.update();

        console.log(`Chart ${chartId} updated with data:`, chartData); // Debug log
    } catch (error) {
        console.error(`Error initializing chart ${chartId}:`, error);
        // Show error in UI
        const errorMessage = document.createElement('div');
        errorMessage.style.color = 'red';
        errorMessage.style.padding = '20px';
        errorMessage.textContent = `Error loading data for ${chartId}: ${error.message}`;
        document.getElementById(chartId).parentNode.appendChild(errorMessage);
    }
}

// Initialize all charts when document is loaded
document.addEventListener('DOMContentLoaded', () => {
    console.log('Document loaded, initializing charts...');

    // Initialize each chart with appropriate data source and value key
    initializeChart('cyclesOverTimeChart', 'cyclesOverTime', 'mock-cycles-without-tests.csv', 'cycles');
    initializeChart('complexityMetricsChart', 'complexityMetrics', 'mock-cycles-with-tests.csv', 'cycles');
    initializeChart('trendAnalysisChart', 'trendAnalysis', 'mock-layer-violations.csv', 'violations');
    initializeChart('comparativeMetricsChart', 'comparativeMetrics', 'mock-build-time.csv', 'buildtime');
});