// Chart configurations for history page
const historyChartConfigs = {
    antHistoricalCycles: {
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
    antHistoricalViolations: {
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
                    text: 'Ant Historical Layer Violations Trend'
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

    // Format dates for x-axis
    const labels = rawData.map(row => formatDateTime(row.date, row.time));

    // Create datasets for each value key
    const datasets = valueKeys.map((keyInfo, index) => {
        const colors = [
            { border: '#4169E1', background: 'rgba(65, 105, 225, 0.2)' },  // Royal Blue
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

async function initializeHistoricalChartMultiFile(chartId, configKey, csvFiles, valueKeys) {
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

        // Load and process data from all CSV files
        const allData = [];

        // If csvFiles is a string (single file), convert to array for consistent processing
        const filesArray = Array.isArray(csvFiles) ? csvFiles : [csvFiles];
        const valuesArray = Array.isArray(valueKeys[0]) ? valueKeys : [valueKeys];

        // Load data from each file
        for (let i = 0; i < filesArray.length; i++) {
            const rawData = await fetchCSVData(filesArray[i]);
            const fileValueKeys = valuesArray[i] || valueKeys;

            // For first file, set the labels (assuming all files have same dates)
            if (i === 0) {
                chart.data.labels = rawData.map(row => formatDateTime(row.date, row.time));
            }

            // Process data for this file
            const chartData = processHistoricalData(rawData, fileValueKeys);

            // Apply specific colors based on dataset labels
            chartData.datasets.forEach(dataset => {
                if (dataset.label === 'Cycles With Tests') {
                    dataset.borderColor = '#FFCE56'; // Yellow
                    dataset.backgroundColor = 'rgba(255, 206, 86, 0.2)';
                } else if (dataset.label === 'Cycles Without Tests') {
                    dataset.borderColor = '#4169E1'; // Royal Blue
                    dataset.backgroundColor = 'rgba(65, 105, 225, 0.2)';
                }
            });

            chart.data.datasets = chart.data.datasets.concat(chartData.datasets);
        }

        chart.update();
        console.log(`Historical chart ${chartId} updated with data from multiple files`);
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
    initializeHistoricalChart('mavenHistoricalCyclesChart', 'historicalCycles', 'archunit-history/maven_cycles_history.csv', [
        { key: 'all_modules_no_tests', label: 'Cycles Without Tests' },
        { key: 'all_modules_with_tests', label: 'Cycles With Tests' }
    ]);

    // Initialize Ant historical cycles chart with data from two files
    initializeHistoricalChartMultiFile('antHistoricalCyclesChart', 'antHistoricalCycles',
        ['archunit-history/ant_cycles_history_no_tests.csv', 'archunit-history/ant_cycles_history_with_tests.csv'],
        [
            [{ key: 'all_modules', label: 'Cycles Without Tests' }],
            [{ key: 'all_modules', label: 'Cycles With Tests' }]
        ]
    );

    // Initialize historical layer violations chart
    initializeHistoricalChart('antHistoricalLayerViolationsChart', 'antHistoricalViolations', 'archunit-history/ant_layers_history.csv', [
        { key: 'violations', label: 'Layer Violations' }
    ]);

    // Initialize historical layer violations chart
    initializeHistoricalChart('mavenHistoricalLayerViolationsChart', 'historicalViolations', 'archunit-history/maven_layers_history.csv', [
        { key: 'violations', label: 'Layer Violations' }
    ]);

    // Initialize historical build time chart
    initializeHistoricalChart('mavenHistoricalBuildTimeChart', 'historicalBuildTime', 'archunit-history/maven_build_time_history.csv', [
        { key: 'buildtime', label: 'Build Time (seconds)' }
    ]);
});