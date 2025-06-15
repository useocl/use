// === GEMEINSAME BASIS-BIBLIOTHEK ===
// Alle geteilte Funktionalität zwischen den Dashboards

// === KONSTANTEN ===
const COLORS = {
    BLUE: { border: '#4169E1', background: 'rgba(65, 105, 225, 0.2)' },
    YELLOW: { border: '#FFCE56', background: 'rgba(255, 206, 86, 0.2)' },
    RED: { border: '#FF0000', background: 'transparent' },
    GREEN: { border: '#32CD32', background: 'rgba(50, 205, 50, 0.2)' },
    BAR_COLORS: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40']
};

// === DATEN-UTILITIES ===
const DataUtils = {
    // Datum/Zeit formatieren
    formatDateTime(dateStr, timeStr) {
        const [year, month, day] = dateStr.split('-');
        const [hours, minutes] = timeStr.split(':');
        return `${day}/${month}/${year.substring(2)} ${hours}:${minutes}`;
    },

    // CSV-Daten laden
    async fetchCSV(filename) {
        try {
            const response = await fetch(filename);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const csvText = await response.text();

            return new Promise((resolve, reject) => {
                Papa.parse(csvText, {
                    complete: (results) => resolve(results.data),
                    error: reject,
                    header: true,
                    skipEmptyLines: true,
                    dynamicTyping: true
                });
            });
        } catch (error) {
            console.error(`Error loading CSV from ${filename}:`, error);
            throw error;
        }
    }
};

// === CHART-UTILITIES ===
const ChartUtils = {
    // Standard-Chart-Konfiguration
    createBaseConfig(title, yAxisLabel, formatValue = (value) => value) {
        return {
            type: 'line',
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: { position: 'top' },
                    title: { display: true, text: title },
                    tooltip: {
                        callbacks: {
                            label: (context) => {
                                const label = context.dataset.label || '';
                                const value = formatValue(context.parsed.y);
                                const commit = context.dataset.commitInfo?.[context.dataIndex];
                                return commit ?
                                    `${label}: ${value}\nCommit: ${commit}` :
                                    `${label}: ${value}`;
                            }
                        }
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        title: { display: true, text: yAxisLabel }
                    },
                    x: {
                        title: { display: true, text: 'Date & Time' }
                    }
                }
            }
        };
    },

    // Line-Chart erstellen
    async createLineChart(canvasId, title, csvPath, valueKey, label, color, threshold = null) {
        const ctx = document.getElementById(canvasId);
        if (!ctx) {
            throw new Error(`Canvas ${canvasId} not found`);
        }

        // Daten laden
        const rawData = await DataUtils.fetchCSV(csvPath);
        const labels = rawData.map(row => DataUtils.formatDateTime(row.date, row.time));

        const datasets = [{
            label,
            data: rawData.map(row => row[valueKey]),
            borderColor: color.border,
            backgroundColor: color.background,
            tension: 0.4,
            commitInfo: rawData.map(row => row.commit)
        }];

        // Threshold-Linie hinzufügen falls gewünscht
        if (threshold !== null) {
            datasets.push({
                label: 'Threshold',
                data: new Array(rawData.length).fill(threshold),
                borderColor: COLORS.RED.border,
                backgroundColor: COLORS.RED.background,
                borderWidth: 2,
                pointRadius: 0,
                tension: 0
            });
        }

        // Chart konfigurieren
        const config = this.createBaseConfig(title, label);

        // Chart erstellen
        return new Chart(ctx, {
            ...config,
            data: { labels, datasets }
        });
    },

    // Bar-Chart erstellen
    async createBarChart(canvasId, title, csvPath, packageNames, suffix) {
        const ctx = document.getElementById(canvasId);
        if (!ctx) {
            throw new Error(`Canvas ${canvasId} not found`);
        }

        // Daten laden
        const rawData = await DataUtils.fetchCSV(csvPath);
        const latestEntry = rawData[rawData.length - 1];

        // Packages mit Cycles finden
        const packagesWithCycles = packageNames.filter(pkg =>
            latestEntry[pkg + suffix] > 0
        );
        const cycleValues = packagesWithCycles.map(pkg =>
            latestEntry[pkg + suffix]
        );

        // Chart konfigurieren
        const config = this.createBaseConfig(title, 'Number of Cycles');
        config.type = 'bar';
        config.options.plugins.legend.display = false;
        config.options.scales.x.title.text = 'Packages';
        config.options.scales.y.ticks = { stepSize: 1 };

        // Chart erstellen
        return new Chart(ctx, {
            ...config,
            data: {
                labels: packagesWithCycles,
                datasets: [{
                    data: cycleValues,
                    backgroundColor: COLORS.BAR_COLORS.slice(0, packagesWithCycles.length)
                }]
            }
        });
    },

    // Error-Handling für Charts
    handleChartError(canvasId, error) {
        console.error(`Error creating chart ${canvasId}:`, error);

        const canvas = document.getElementById(canvasId);
        if (canvas?.parentNode) {
            const errorDiv = document.createElement('div');
            errorDiv.className = 'chart-error';
            errorDiv.style.cssText = 'color: red; padding: 20px; text-align: center;';
            errorDiv.textContent = `Error loading ${canvasId}: ${error.message}`;
            canvas.parentNode.appendChild(errorDiv);
        }
    }
};

// === DASHBOARD-UTILITIES ===
const DashboardUtils = {
    // Mehrere Charts parallel erstellen
    async createChartsParallel(chartConfigs) {
        const charts = {};

        const chartPromises = chartConfigs.map(async (config) => {
            try {
                let chart;

                if (config.type === 'line') {
                    chart = await ChartUtils.createLineChart(
                        config.canvasId,
                        config.title,
                        config.csvPath,
                        config.valueKey,
                        config.label,
                        config.color,
                        config.threshold
                    );
                } else if (config.type === 'bar') {
                    chart = await ChartUtils.createBarChart(
                        config.canvasId,
                        config.title,
                        config.csvPath,
                        config.packageNames,
                        config.suffix
                    );
                }

                charts[config.canvasId] = chart;
                console.log(`Chart ${config.canvasId} created successfully`);

            } catch (error) {
                ChartUtils.handleChartError(config.canvasId, error);
            }
        });

        await Promise.allSettled(chartPromises);
        return charts;
    },

    // Alle Charts zerstören
    destroyCharts(charts) {
        Object.values(charts).forEach(chart => {
            if (chart && typeof chart.destroy === 'function') {
                chart.destroy();
            }
        });
    }
};

// Export für andere Module
if (typeof window !== 'undefined') {
    window.DashboardUtils = { DataUtils, ChartUtils, DashboardUtils, COLORS };
}