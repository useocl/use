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
    // Bar chart configuration (ersetzt die pie chart configs)
    cyclesBreakdown: {
        type: 'bar',
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false, // Bei single dataset nicht nötig
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
    cyclesWithTestsBreakdown: {
        type: 'bar',
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false, // Bei single dataset nicht nötig
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

// Process data for charts with multiple datasets
function processMultiDatasetChartData(rawData, valueKeys) {
    console.log(`Processing data for multiple datasets:`, rawData);

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

// We can remove this function since we're using processMultiDatasetChartData for all charts

// Process cycle breakdown data for pie chart with support for "no_tests" or "with_tests"
function processCycleBreakdownData(rawData, includeTests = false) {
    // Get the most recent entry (last one in the array)
    if (!rawData || rawData.length === 0) {
        return { labels: [], datasets: [{ data: [], backgroundColor: [] }] };
    }

    const latestEntry = rawData[rawData.length - 1];

    // Determine suffix based on includeTests parameter
    const suffix = includeTests ? '_with_tests' : '_no_tests';

    // Package names and their corresponding values
    const packageNames = ['analysis', 'api', 'config', 'gen', 'graph', 'main', 'parser', 'uml', 'util'];

    // Filter to only include packages with non-zero cycles
    const packagesWithCycles = packageNames.filter(pkg => latestEntry[pkg + suffix] > 0);
    const cycleValues = packagesWithCycles.map(pkg => latestEntry[pkg + suffix]);

    // Generate colors for the pie chart
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

// Helper function to get the appropriate dataset label based on the value key
function getDatasetLabel(valueKey) {
    switch(valueKey) {
        case 'all_modules_no_tests':
            return 'Number of Cycles Without Tests';
        case 'all_modules_with_tests':
            return 'Number of Cycles With Tests';
        case 'violations':
            return 'Violations';
        case 'buildtime':
            return 'Build Time';
        default:
            return 'Value';
    }
}

// Initialize a chart with multiple datasets (similar to history.js approach)
async function initializeChart(chartId, configKey, csvFile, valueKeys) {
    try {
        // Create initial empty chart
        const ctx = document.getElementById(chartId);
        const chart = new Chart(ctx, {
            type: chartConfigs[configKey].type,
            data: {
                labels: [],
                datasets: []
            },
            options: chartConfigs[configKey].options
        });

        // Load and update with real data
        const rawData = await fetchCSVData(csvFile);
        const chartData = processMultiDatasetChartData(rawData, valueKeys);
        chart.data = chartData;
        chart.update();

        console.log(`Chart ${chartId} updated with multiple datasets:`, chartData);
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

// We can remove this function since we're using initializeChart for all charts
// Initialize the cycle breakdown pie chart
async function initializeCycleBreakdownChart(chartId, configKey, csvFile, includeTests = false) {
    try {
        // Create initial empty chart
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

        // Load and update with real data
        const rawData = await fetchCSVData(csvFile);
        const chartData = processCycleBreakdownData(rawData, includeTests);
        chart.data = chartData;
        chart.update();

        // Add subtitle for the current test run
        if (rawData && rawData.length > 0) {
            const latestEntry = rawData[rawData.length - 1];
            const testType = includeTests ? "including tests" : "excluding tests";

            // Find the existing description element within the same card
            const cardElement = ctx.closest('.card');
            const descriptionElement = cardElement.querySelector('.card-description');

            if (descriptionElement) {
                // Update the existing description
                descriptionElement.textContent = `Distribution of cyclic dependencies across different packages (${testType}) for the most current ArchUnit test run (${formatDateTime(latestEntry.date, latestEntry.time)}, commit: ${latestEntry.commit})`;
            }
        }
        console.log(`Pie chart ${chartId} updated with data:`, chartData);
    } catch (error) {
        console.error(`Error initializing pie chart ${chartId}:`, error);
        // Show error in UI
        const errorMessage = document.createElement('div');
        errorMessage.style.color = 'red';
        errorMessage.style.padding = '20px';
        errorMessage.textContent = `Error loading data for ${chartId}: ${error.message}`;
        document.getElementById(chartId).parentNode.appendChild(errorMessage);
    }
}

// Function to get the latest update time from CSV data
async function getLatestUpdateTime() {
    try {
        const rawData = await fetchCSVData('archunit-results/cycles-tests.csv');

        if (rawData && rawData.length > 0) {
            // Get the most recent entry (last in array)
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

// Function to update the "Last Updated" display
async function updateLastUpdatedDisplay() {
    const lastUpdatedElement = document.getElementById('lastUpdated');

    try {
        const updateInfo = await getLatestUpdateTime();

        if (updateInfo) {
            const formattedDateTime = formatDateTime(updateInfo.date, updateInfo.time);
            const shortCommit = updateInfo.commit.substring(0, 7); // Show first 7 chars of commit

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
    console.log('Document loaded, initializing charts...');
    updateLastUpdatedDisplay();

    // Initialize cyclic dependencies chart with both metrics (with and without tests)
    initializeChart('cyclesOverTimeChart', 'cyclesOverTime', 'archunit-results/cycles-tests.csv', [
        { key: 'all_modules_no_tests', label: 'Cycles Without Tests' },
        { key: 'all_modules_with_tests', label: 'Cycles With Tests' }
    ]);

// Initialize pie charts for breakdown analysis
    initializeCycleBreakdownChart('cyclesBreakdownChart', 'cyclesBreakdown', 'archunit-results/cycles-tests.csv', false);
    initializeCycleBreakdownChart('cyclesWithTestsBreakdownChart', 'cyclesWithTestsBreakdown', 'archunit-results/cycles-tests.csv', true);

    // Initialize other charts - now using the multi-dataset function with single items
    initializeChart('layerViolationsChart', 'layerViolationsOverTime', 'archunit-results/layer-violations.csv', [
        { key: 'violations', label: 'Violations' }
    ]);
    initializeChart('comparativeMetricsChart', 'comparativeMetrics', 'archunit-results/build-times.csv', [
        { key: 'buildtime', label: 'Build Time (seconds)' }
    ]);
});