// === HISTORY DASHBOARD ===

// === KONFIGURATION ===
const CSV_PATHS = {
    ANT_CYCLES_NO_TESTS: 'archunit-history/ant_cycles_history_no_tests.csv',
    ANT_CYCLES_WITH_TESTS: 'archunit-history/ant_cycles_history_with_tests.csv',
    MAVEN_CYCLES: 'archunit-history/maven_cycles_history.csv',
    ANT_LAYERS: 'archunit-history/ant_layers_history.csv',
    MAVEN_LAYERS: 'archunit-history/maven_layers_history.csv',
    MAVEN_BUILD_TIME: 'archunit-history/maven_build_time_history.csv'
};

// Chart-Konfigurationen als einfache Objekte
const CHART_CONFIGS = [
    {
        type: 'line',
        canvasId: 'antHistoricalCyclesNoTestsChart',
        title: 'Historical Cyclic Dependencies - Ant (No Tests)',
        csvPath: CSV_PATHS.ANT_CYCLES_NO_TESTS,
        valueKey: 'all_modules',
        label: 'Cycles Without Tests',
        color: window.DashboardUtils.COLORS.BLUE
    },
    {
        type: 'line',
        canvasId: 'mavenHistoricalCyclesNoTestsChart',
        title: 'Historical Cyclic Dependencies - Maven (No Tests)',
        csvPath: CSV_PATHS.MAVEN_CYCLES,
        valueKey: 'all_modules_no_tests',
        label: 'Cycles Without Tests',
        color: window.DashboardUtils.COLORS.BLUE
    },
    {
        type: 'line',
        canvasId: 'antHistoricalCyclesWithTestsChart',
        title: 'Historical Cyclic Dependencies - Ant (With Tests)',
        csvPath: CSV_PATHS.ANT_CYCLES_WITH_TESTS,
        valueKey: 'all_modules',
        label: 'Cycles With Tests',
        color: window.DashboardUtils.COLORS.YELLOW
    },
    {
        type: 'line',
        canvasId: 'mavenHistoricalCyclesWithTestsChart',
        title: 'Historical Cyclic Dependencies - Maven (With Tests)',
        csvPath: CSV_PATHS.MAVEN_CYCLES,
        valueKey: 'all_modules_with_tests',
        label: 'Cycles With Tests',
        color: window.DashboardUtils.COLORS.YELLOW
    },
    {
        type: 'line',
        canvasId: 'antHistoricalLayerViolationsChart',
        title: 'Historical Architecture Layer Violations - Ant',
        csvPath: CSV_PATHS.ANT_LAYERS,
        valueKey: 'violations',
        label: 'Layer Violations',
        color: window.DashboardUtils.COLORS.RED
    },
    {
        type: 'line',
        canvasId: 'mavenHistoricalLayerViolationsChart',
        title: 'Historical Architecture Layer Violations - Maven',
        csvPath: CSV_PATHS.MAVEN_LAYERS,
        valueKey: 'violations',
        label: 'Layer Violations',
        color: window.DashboardUtils.COLORS.RED
    },
    {
        type: 'line',
        canvasId: 'mavenHistoricalBuildTimeChart',
        title: 'Historical Build Time - Maven',
        csvPath: CSV_PATHS.MAVEN_BUILD_TIME,
        valueKey: 'buildtime',
        label: 'Build Time (seconds)',
        color: window.DashboardUtils.COLORS.GREEN
    }
];

// === DASHBOARD-FUNKTIONEN ===

// Dashboard initialisieren
async function initHistoryDashboard() {
    console.log('Initializing History Dashboard...');

    try {
        const charts = await window.DashboardUtils.DashboardUtils.createChartsParallel(CHART_CONFIGS);
        console.log('History Dashboard initialization complete');
        return charts;
    } catch (error) {
        console.error('Failed to initialize dashboard:', error);
        return {};
    }
}

// === INITIALIZATION ===
let historyCharts = {};

document.addEventListener('DOMContentLoaded', async () => {
    // Warten bis Utils geladen sind
    if (!window.DashboardUtils) {
        console.error('DashboardUtils not loaded. Include dashboard-utils.js first.');
        return;
    }

    try {
        historyCharts = await initHistoryDashboard();
    } catch (error) {
        console.error('Failed to initialize history dashboard:', error);
    }
});

// Cleanup
window.addEventListener('beforeunload', () => {
    window.DashboardUtils.DashboardUtils.destroyCharts(historyCharts);
});