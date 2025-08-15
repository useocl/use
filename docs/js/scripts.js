// === INDEX DASHBOARD ===

// === KONFIGURATION ===
const CSV_PATHS = {
    CYCLES_TESTS: 'archunit-results/cycles-tests.csv',
    LAYER_VIOLATIONS: 'archunit-results/layer-violations.csv',
    BUILD_TIMES: 'archunit-results/build-times.csv'
};

const PACKAGE_NAMES = ['analysis', 'api', 'config', 'gen', 'graph', 'main', 'parser', 'uml', 'util'];

// Chart-Konfigurationen als einfache Objekte
const CHART_CONFIGS = [
    {
        type: 'line',
        canvasId: 'cyclesWithoutTestsChart',
        title: 'Cyclic Dependencies',
        csvPath: CSV_PATHS.CYCLES_TESTS,
        valueKey: 'all_modules_no_tests',
        label: 'Cycles Without Tests',
        color: window.DashboardUtils.COLORS.BLUE,
        threshold: 56
    },
    {
        type: 'bar',
        canvasId: 'cyclesWithoutTestsBreakdownChart',
        title: 'Breakdown of Cycles per Package',
        csvPath: CSV_PATHS.CYCLES_TESTS,
        packageNames: PACKAGE_NAMES,
        suffix: '_no_tests'
    },
    {
        type: 'line',
        canvasId: 'cyclesWithTestsChart',
        title: 'Cyclic Dependencies with Tests',
        csvPath: CSV_PATHS.CYCLES_TESTS,
        valueKey: 'all_modules_with_tests',
        label: 'Cycles With Tests',
        color: window.DashboardUtils.COLORS.BLUE
    },
    {
        type: 'bar',
        canvasId: 'cyclesWithTestsBreakdownChart',
        title: 'Breakdown of Cycles per Package Incl. Tests',
        csvPath: CSV_PATHS.CYCLES_TESTS,
        packageNames: PACKAGE_NAMES,
        suffix: '_with_tests'
    },
    {
        type: 'line',
        canvasId: 'layerViolationsChart',
        title: 'Layer Architecture Violations',
        csvPath: CSV_PATHS.LAYER_VIOLATIONS,
        valueKey: 'violations',
        label: 'Violations',
        color: window.DashboardUtils.COLORS.BLUE,
        threshold: 1
    },
    {
        type: 'line',
        canvasId: 'buildTimesChart',
        title: 'Build Time',
        csvPath: CSV_PATHS.BUILD_TIMES,
        valueKey: 'buildtime',
        label: 'Build Time (seconds)',
        color: window.DashboardUtils.COLORS.BLUE
    }
];

// === DASHBOARD-FUNKTIONEN ===

// "Last Updated" Anzeige aktualisieren
async function updateLastUpdatedDisplay() {
    const lastUpdatedElement = document.getElementById('lastUpdated');
    if (!lastUpdatedElement) return;

    try {
        const rawData = await window.DashboardUtils.DataUtils.fetchCSV(CSV_PATHS.CYCLES_TESTS);

        if (rawData && rawData.length > 0) {
            const latestEntry = rawData[rawData.length - 1];
            const formattedDateTime = window.DashboardUtils.DataUtils.formatDateTime(
                latestEntry.date,
                latestEntry.time
            );
            const shortCommit = latestEntry.commit.substring(0, 7);

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

// Dashboard initialisieren
async function initIndexDashboard() {
    console.log('Initializing Index Dashboard...');

    try {
        // Last Updated anzeigen und Charts erstellen
        await Promise.all([
            updateLastUpdatedDisplay(),
            window.DashboardUtils.DashboardUtils.createChartsParallel(CHART_CONFIGS)
        ]);

        console.log('Index Dashboard initialization complete');
        return true;
    } catch (error) {
        console.error('Failed to initialize dashboard:', error);
        return false;
    }
}

// === INITIALIZATION ===
let indexCharts = {};

document.addEventListener('DOMContentLoaded', async () => {
    // Warten bis Utils geladen sind
    if (!window.DashboardUtils) {
        console.error('DashboardUtils not loaded. Include dashboard-utils.js first.');
        return;
    }

    try {
        indexCharts = await initIndexDashboard();
    } catch (error) {
        console.error('Failed to initialize index dashboard:', error);
    }
});

// Cleanup
window.addEventListener('beforeunload', () => {
    window.DashboardUtils.DashboardUtils.destroyCharts(indexCharts);
});