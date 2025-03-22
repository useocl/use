// Chart configurations for history page
const historyChartConfigs = {
    historicalCycles: {
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
                    text: 'Historical Cyclic Dependencies Trend'
                },
                tooltip: {
                    callbacks: {
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
    historicalViolations: {
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
                    text: 'Historical Layer Violations Trend'
                },
                tooltip: {
                    callbacks: {
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
                    ticks: {
                        stepSize: 1
                    },
                    title: {
                        display: true,
                        text: 'Number of Violations'
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
    historicalBuildTime: {
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
                    text: 'Historical Build Time Trend'
                },
                tooltip: {
                    callbacks: {
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

// Function to format date and time (re-used from scripts.js)
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

// Function to fetch and parse CSV data (re-used from scripts.js)
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

// Process data for historical charts - includes larger date ranges
function processHistoricalData(rawData, valueKeys) {
    console.log(`Processing historical data for ${valueKeys}:`, rawData);

    // Sort data by date and time to ensure chronological order
    rawData.sort((a, b) => {
        const dateA = new Date(`${a.date}T${a.time}`);
        const dateB = new Date(`${b.date}T${b.time}`);
        return dateA - dateB;
    });

    // Format dates for x-axis
    const labels = rawData.map(row => formatDateTime(row.date, row.time));

    // Create datasets for each value key
    const datasets = valueKeys.map((keyInfo, index) => {
        const colors = [
            { border: '#4169E1', background: 'rgba(65, 105, 225, 0.2)' },  // Royal Blue
            { border: '#FF6384', background: 'rgba(255, 99, 132, 0.2)' },  // Red
            { border: '#36A2EB', background: 'rgba(54, 162, 235, 0.2)' },  // Blue
            { border: '#FFCE56', background: 'rgba(255, 206, 86, 0.2)' }   // Yellow
        ];

        return {
            label: keyInfo.label,
            data: rawData.map(row => row[keyInfo.key]),
            borderColor: colors[index % colors.length].border,
            backgroundColor: colors[index % colors.length].background,
            tension: 0.4,
            // Store commit information for each data point
            commitInfo: rawData.map(row => row.commit)
        };
    });

    return { labels, datasets };
}

// Initialize a historical chart with multiple datasets
async function initializeHistoricalChart(chartId, configKey, csvFile, valueKeys) {
    try {
        // Create initial empty chart
        const ctx = document.getElementById(chartId);
        const chart = new Chart(ctx, {
            type: historyChartConfigs[configKey].type,
            data: {
                labels: [],
                datasets: []
            },
            options: historyChartConfigs[configKey].options
        });

        // Load and update with real data
        const rawData = await fetchCSVData(csvFile);
        const chartData = processHistoricalData(rawData, valueKeys);
        chart.data = chartData;
        chart.update();

        console.log(`Historical chart ${chartId} updated with data:`, chartData);
    } catch (error) {
        console.error(`Error initializing historical chart ${chartId}:`, error);
        // Show error in UI
        const errorMessage = document.createElement('div');
        errorMessage.style.color = 'red';
        errorMessage.style.padding = '20px';
        errorMessage.textContent = `Error loading data for ${chartId}: ${error.message}`;
        document.getElementById(chartId).parentNode.appendChild(errorMessage);
    }
}

// Initialize all historical charts when document is loaded
document.addEventListener('DOMContentLoaded', () => {
    console.log('History page loaded, initializing historical charts...');

    // Initialize historical cycles chart with both metrics (with and without tests)
    initializeHistoricalChart('historicalCyclesChart', 'historicalCycles', 'cycles-tests.csv', [
        { key: 'all_modules_no_tests', label: 'Cycles Without Tests' },
        { key: 'all_modules_with_tests', label: 'Cycles With Tests' }
    ]);

    // Initialize historical layer violations chart
    initializeHistoricalChart('historicalViolationsChart', 'historicalViolations', 'layer-violations.csv', [
        { key: 'violations', label: 'Layer Violations' }
    ]);

    // Initialize historical build time chart
    initializeHistoricalChart('historicalBuildTimeChart', 'historicalBuildTime', 'build-times.csv', [
        { key: 'buildtime', label: 'Build Time (seconds)' }
    ]);
});