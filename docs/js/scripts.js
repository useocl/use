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
    cyclesBreakdown: {
        type: 'bar',
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false,
                },
                title: {
                    display: true,
                    text: 'Breakdown of Cycles per Package'
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            const label = context.label || '';
                            const value = context.raw;
                            return `${label}: ${value} cycles`;
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
                        text: 'Number of Cycles'
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Packages'
                    }
                }
            }
        }
    },
    cyclesWithTests: {
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
    cyclesWithTestsBreakdown: {
        type: 'bar',
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false,
                },
                title: {
                    display: true,
                    text: 'Breakdown of Cycles per Package Incl. Tests'
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            const label = context.label || '';
                            const value = context.raw;
                            return `${label}: ${value} cycles`;
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
                        text: 'Number of Cycles'
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Packages'
                    }
                }
            }
        }
    },
    layerViolationsOverTime: {
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
                        text: 'No. of Violations)'
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

// Format date & time as DD/MM/YY HH:MM
function formatDateTime(dateStr, timeStr) {
    const [year, month, day] = dateStr.split('-');
    const timeParts = timeStr.split(':');
    const hours = timeParts[0];
    const minutes = timeParts[1];
    return `${day}/${month}/${year.substring(2)} ${hours}:${minutes}`;
}

// Fetch & parse CSV data
async function fetchCSVData(filename) {
    try {
        const response = await fetch(filename);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const csvText = await response.text();

        return new Promise((resolve, reject) => {
            Papa.parse(csvText, {
                complete: (results) => {
                    resolve(results.data);
                },
                error: (error) => {
                    console.error('Papa Parse error:', error); // Debug log
                    reject(error);
                },
                header: true,
                skipEmptyLines: true,
                dynamicTyping: true
            });
        });
    } catch (error) {
        console.error(`Error loading metrics from ${filename}:`, error);
        throw error;
    }
}

// Process data for line charts with optional threshold
function processLineChartData(rawData, valueKeys, threshold = null) {

    const labels = rawData.map(row => formatDateTime(row.date, row.time));

    const datasets = valueKeys.map((keyInfo, index) => {
        const colors = [
            { border: '#4169E1', background: 'rgba(65, 105, 225, 0.2)' }
        ];

        return {
            label: keyInfo.label,
            data: rawData.map(row => row[keyInfo.key]),
            borderColor: colors[index % colors.length].border,
            backgroundColor: colors[index % colors.length].background,
            tension: 0.4,
            commitInfo: rawData.map(row => row.commit)
        };
    });

    // Add threshold line if specified
    if (threshold !== null) {
        datasets.push({
            label: 'Threshold',
            data: new Array(rawData.length).fill(threshold),
            borderColor: '#FF0000',  // Red
            backgroundColor: 'transparent',
            //borderDash: [5, 5],  // Dashed line
            borderWidth: 2,
            pointRadius: 0,
            tension: 0,
            commitInfo: rawData.map(row => row.commit)
        });
    }

    return { labels, datasets };
}

// Process data for bar charts (breakdown by package)
function processBarChartData(rawData, includeTests = false) {
    if (!rawData || rawData.length === 0) {
        return { labels: [], datasets: [{ data: [], backgroundColor: [] }] };
    }

    const latestEntry = rawData[rawData.length - 1];
    const suffix = includeTests ? '_with_tests' : '_no_tests';
    const packageNames = ['analysis', 'api', 'config', 'gen', 'graph', 'main', 'parser', 'uml', 'util'];
    const packagesWithCycles = packageNames.filter(pkg => latestEntry[pkg + suffix] > 0);
    const cycleValues = packagesWithCycles.map(pkg => latestEntry[pkg + suffix]);
    const colors = [
        '#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0',
        '#9966FF', '#FF9F40', '#6A8D73', '#D9534F',
        '#5BC0DE', '#F0AD4E'
    ];

    return {
        labels: packagesWithCycles,
        datasets: [{
            data: cycleValues,
            backgroundColor: colors.slice(0, packagesWithCycles.length)
        }]
    };
}

// Initialize line charts
async function initializeLineChart(chartId, configKey, csvFile, valueKeys, threshold = null) {
    try {
        const ctx = document.getElementById(chartId);
        const chart = new Chart(ctx, {
            type: chartConfigs[configKey].type,
            data: {
                labels: [],
                datasets: []
            },
            options: chartConfigs[configKey].options
        });
        const rawData = await fetchCSVData(csvFile);
        const chartData = processLineChartData(rawData, valueKeys, threshold);
        chart.data = chartData;
        chart.update();
    } catch (error) {
        console.error(`Error initializing chart ${chartId}:`, error);
        const errorMessage = document.createElement('div');
        errorMessage.style.color = 'red';
        errorMessage.style.padding = '20px';
        errorMessage.textContent = `Error loading data for ${chartId}: ${error.message}`;
        document.getElementById(chartId).parentNode.appendChild(errorMessage);
    }
}

// Initialize bar charts (breakdown charts)
async function initializeBarChart(chartId, configKey, csvFile, includeTests = false) {
    try {
        const ctx = document.getElementById(chartId);
        const chart = new Chart(ctx, {
            type: chartConfigs[configKey].type,
            data: {
                labels: [],
                datasets: [{
                    data: [],
                    backgroundColor: []
                }]
            },
            options: chartConfigs[configKey].options
        });
        const rawData = await fetchCSVData(csvFile);
        const chartData = processBarChartData(rawData, includeTests);
        chart.data = chartData;
        chart.update();
    } catch (error) {
        console.error(`Error initializing pie chart ${chartId}:`, error);
        const errorMessage = document.createElement('div');
        errorMessage.style.color = 'red';
        errorMessage.style.padding = '20px';
        errorMessage.textContent = `Error loading data for ${chartId}: ${error.message}`;
        document.getElementById(chartId).parentNode.appendChild(errorMessage);
    }
}

async function getLatestUpdateTime() {
    try {
        const rawData = await fetchCSVData('archunit-results/cycles-tests.csv');

        if (rawData && rawData.length > 0) {
            const latestEntry = rawData[rawData.length - 1];
            return {
                date: latestEntry.date,
                time: latestEntry.time,
                commit: latestEntry.commit
            };
        }
        return null;
    } catch (error) {
        console.error('Error fetching latest update time:', error);
        return null;
    }
}

async function updateLastUpdatedDisplay() {
    const lastUpdatedElement = document.getElementById('lastUpdated');

    try {
        const updateInfo = await getLatestUpdateTime();

        if (updateInfo) {
            const formattedDateTime = formatDateTime(updateInfo.date, updateInfo.time);
            const shortCommit = updateInfo.commit.substring(0, 7);

            lastUpdatedElement.innerHTML = `
                <span>Last updated: </span>
                <span class="update-time">${formattedDateTime}</span>
                <span> (Commit: ${shortCommit})</span>
            `;
        } else {
            lastUpdatedElement.innerHTML = '<span>Update time unavailable</span>';
        }
    } catch (error) {
        console.error('Error updating last updated display:', error);
        lastUpdatedElement.innerHTML = '<span>Error loading update time</span>';
    }
}

// Initialize all charts when document is loaded
document.addEventListener('DOMContentLoaded', () => {
    updateLastUpdatedDisplay();

    // 1. All cycles without tests
    initializeLineChart('cyclesOverTimeChart', 'cyclesOverTime', 'archunit-results/cycles-tests.csv', [
        { key: 'all_modules_no_tests', label: 'Cycles Without Tests' },], 56);
    // 2. Breakdown without tests
    initializeBarChart('cyclesBreakdownChart', 'cyclesBreakdown', 'archunit-results/cycles-tests.csv', false);
    // 3. All cycles with tests
    initializeLineChart('cyclesWithTestsChart', 'cyclesWithTests', 'archunit-results/cycles-tests.csv', [{ key: 'all_modules_with_tests', label: 'Cycles With Tests' }]);
    // 4. Breakdown with tests
    initializeBarChart('cyclesWithTestsBreakdownChart', 'cyclesWithTestsBreakdown', 'archunit-results/cycles-tests.csv', true);
    // 5. Layer violations
    initializeLineChart('layerViolationsChart', 'layerViolationsOverTime', 'archunit-results/layer-violations.csv', [
        { key: 'violations', label: 'Violations' }
    ], 1);
    // 6. Build times
    initializeLineChart('comparativeMetricsChart', 'comparativeMetrics', 'archunit-results/build-times.csv', [
        { key: 'buildtime', label: 'Build Time (seconds)' }
    ]);
});