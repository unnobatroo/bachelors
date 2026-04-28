// ============================================================
// PROJECT CARDS - Data and rendering logic
// ============================================================
(function projectCardsInit() {
  const projectsContainer = document.getElementById('projectCards');
  const sortTitleBtn = document.getElementById('sortTitle');
  const sortDateBtn = document.getElementById('sortDate');

  // Exit early if not on homepage
  if (!projectsContainer) return;

  // Sample project data - customize with your actual projects
  let projects = [
    {
      id: 1,
      title: 'Portfolio Website',
      date: new Date(2025, 0, 15),
      image: 'media/luna.png',
      description: 'A responsive portfolio showcasing my design and development work.',
      link: '#'
    },
    {
      id: 2,
      title: 'Web App Dashboard',
      date: new Date(2024, 8, 20),
      image: 'media/raincaller.png',
      description: 'Interactive dashboard for data visualization and analytics.',
      link: '#'
    },
    {
      id: 3,
      title: 'Branding Project',
      date: new Date(2024, 5, 10),
      image: 'media/sheep.png',
      description: 'Complete brand identity design including logo and guidelines.',
      link: '#'
    },
    {
      id: 4,
      title: 'Mobile App UI',
      date: new Date(2024, 11, 5),
      image: 'media/unocards.png',
      description: 'User interface design for iOS and Android applications.',
      link: '#'
    }
  ];

  // Sort state tracking
  let currentSortType = 'title';
  let sortAscending = true;

  // Sort projects based on current state
  function sortProjects() {
    projects.sort((a, b) => {
      let compareA, compareB;

      if (currentSortType === 'title') {
        compareA = a.title.toLowerCase();
        compareB = b.title.toLowerCase();
      } else if (currentSortType === 'date') {
        compareA = a.date;
        compareB = b.date;
      }

      if (sortAscending) {
        return compareA < compareB ? -1 : compareA > compareB ? 1 : 0;
      } else {
        return compareA > compareB ? -1 : compareA < compareB ? 1 : 0;
      }
    });
  }

  // Update sort button states and labels
  function updateSortButtons() {
    // Reset all buttons
    sortTitleBtn.classList.remove('sort-btn-active');
    sortDateBtn.classList.remove('sort-btn-active');

    if (currentSortType === 'title') {
      sortTitleBtn.classList.add('sort-btn-active');
      sortTitleBtn.textContent = sortAscending ? 'Title: A-Z' : 'Title: Z-A';
    } else {
      sortDateBtn.classList.add('sort-btn-active');
      sortDateBtn.textContent = sortAscending ? 'Date: Newest' : 'Date: Oldest';
    }
  }

  // Render project cards to the page
  function renderCards() {
    sortProjects();
    projectsContainer.innerHTML = projects
      .map(
        (project) => `
          <div class="card" data-project-id="${project.id}">
            <img src="${project.image}" alt="${project.title}" class="card-image">
            <div class="card-header">
              <h4 class="card-title">${project.title}</h4>
            </div>
            <div class="card-content">
              <p class="card-body">${project.description}</p>
              <div class="card-actions">
                <a href="${project.link}" class="card-btn">View Project</a>
              </div>
            </div>
          </div>
        `
      )
      .join('');
  }

  // Handle Title button click - toggle between A-Z and Z-A
  sortTitleBtn.addEventListener('click', () => {
    if (currentSortType === 'title') {
      sortAscending = !sortAscending;
    } else {
      currentSortType = 'title';
      sortAscending = true;
    }
    updateSortButtons();
    renderCards();
  });

  // Handle Date button click - toggle between Newest and Oldest
  sortDateBtn.addEventListener('click', () => {
    if (currentSortType === 'date') {
      sortAscending = !sortAscending;
    } else {
      currentSortType = 'date';
      sortAscending = false; // Newest first by default
    }
    updateSortButtons();
    renderCards();
  });

  // Initial render
  updateSortButtons();
  renderCards();
})();

// ============================================================
// THEME TOGGLE - Light/Dark Mode
// ============================================================
// Initializes theme and creates toggle button functionality
(function themeManager() {
  const themeToggle = document.getElementById('themeToggle');
  const themeIcon = document.querySelector('.theme-icon');
  const THEME_STORAGE_KEY = 'preferredTheme';
  const CONTRAST_KEY = 'preferredContrast';

  // Check for saved theme preference or system preference
  function getInitialTheme() {
    const saved = localStorage.getItem(THEME_STORAGE_KEY);
    if (saved) return saved;

    // Check if user prefers dark mode in their OS settings
    if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
      return 'dark';
    }
    return 'light';
  }

  // Apply theme to page
  function applyTheme(theme) {
    if (theme === 'dark') {
      document.documentElement.setAttribute('data-theme', 'dark');
      themeIcon.textContent = '☀️';
    } else {
      document.documentElement.removeAttribute('data-theme');
      themeIcon.textContent = '🌙';
    }
    localStorage.setItem(THEME_STORAGE_KEY, theme);
  }

  // Toggle between light and dark themes
  function toggleTheme() {
    const current = document.documentElement.getAttribute('data-theme') || 'light';
    const next = current === 'light' ? 'dark' : 'light';
    applyTheme(next);
    // Rotate icon on click
    themeIcon.style.transform = 'rotate(180deg)';
    setTimeout(() => {
      themeIcon.style.transform = 'rotate(0deg)';
    }, 300);
  }

  // Initialize theme on page load
  const initialTheme = getInitialTheme();
  applyTheme(initialTheme);

  // Initialize contrast setting
  function applyContrast(enabled){
    if(enabled){
      document.documentElement.setAttribute('data-theme','high-contrast');
    } else {
      // restore to preferred theme
      const pref = localStorage.getItem(THEME_STORAGE_KEY) || 'light';
      if(pref === 'dark') document.documentElement.setAttribute('data-theme','dark');
      else document.documentElement.removeAttribute('data-theme');
    }
    localStorage.setItem(CONTRAST_KEY, enabled ? '1' : '0');
  }

  // contrast toggle hookup
  const contrastToggle = document.getElementById('contrastToggle');
  if(contrastToggle){
    const savedContrast = localStorage.getItem(CONTRAST_KEY) === '1';
    applyContrast(savedContrast);
    contrastToggle.addEventListener('click', ()=>{
      const isOn = document.documentElement.getAttribute('data-theme') === 'high-contrast';
      applyContrast(!isOn);
    })
  }

  // Attach click handler
  if (themeToggle) {
    themeToggle.addEventListener('click', toggleTheme);
  }
})();

// ============================================================
// NAVIGATION BUILDER
// ============================================================
// Builds the side navigation dynamically to keep links consistent across all pages
(function buildNav() {
  const nav = document.querySelector('.sidenav');
  if (!nav) return;

  // Define navigation items - easy to add/remove items here
  const items = [
    { href: 'index.html', label: 'Home' },
    { href: 'blog.html', label: 'Blog' },
    { href: 'favs.html', label: 'Favs' }
  ];

  // Generate anchor tags and inject into navigation
  const current = location.pathname.split('/').pop() || 'index.html';
  nav.innerHTML = items
    .map((i) => {
      const isCurrent = i.href === current;
      return `<a href="${i.href}" class="nav-link" ${isCurrent? 'aria-current="page"' : ''}>${i.label}</a>`;
    })
    .join('');
})();

// ============================================================
// HOMEPAGE INITIALIZATION
// ============================================================
// This runs only on the homepage (index.html)
// Handles: greeting, visit tracking, location lookup
(function homePageInit(){
  const greeting = document.getElementById('greeting');
  const tableBody = document.getElementById('locationLog');

  // Exit early if not on homepage
  if (!greeting || !tableBody) return;

  // CONFIGURATION - Modify these constants to change behavior
  const STORAGE_KEY = 'last5Visits';
  const MAX_VISITS = 5;
  const LOCATION_CACHE_KEY = 'cachedLocation';
  const LOCATION_CACHE_TTL_MS = 10 * 60 * 1000; // Cache location for 10 minutes

  // One formatter for all timestamps - ensures consistent date/time display across the table
  const visitTimeFormatter = new Intl.DateTimeFormat('en-GB', {
    weekday: 'short',
    day: '2-digit',
    month: 'short',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
    hour12: false
  });

  // GREETING LOGIC - Returns appropriate greeting based on time of day
  function getGreeting(hour) {
    if (hour >= 5 && hour < 12) return 'Good morning!';
    if (hour >= 12 && hour < 18) return 'Good afternoon!';
    if (hour >= 18 && hour < 22) return 'Good evening!';
    return 'Good night!';
  }

  // FORMAT TIME - Removes unwanted comma from date string
  function formatTime(date) {
    return visitTimeFormatter.format(date).replace(',', '');
  }

  // READ VISITS - Retrieves visitor history from browser storage
  // Returns empty array if storage is empty or corrupted
  function readVisits() {
    try {
      const data = JSON.parse(localStorage.getItem(STORAGE_KEY));
      return Array.isArray(data) ? data : [];
    } catch {
      return [];
    }
  }

  // SAVE VISITS - Stores visitor history in browser storage
  function saveVisits(visits) {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(visits));
  }

  // READ CACHED LOCATION - Gets location from session if not expired
  // Returns null if cache is empty or expired
  function readCachedLocation() {
    try {
      const cached = JSON.parse(sessionStorage.getItem(LOCATION_CACHE_KEY));
      if (!cached) return null;
      // Check if cache has exceeded TTL (time-to-live)
      const isExpired = Date.now() - cached.savedAt > LOCATION_CACHE_TTL_MS;
      return isExpired ? null : cached.value;
    } catch {
      return null;
    }
  }

  // SAVE CACHED LOCATION - Stores location with timestamp
  function saveCachedLocation(value) {
    const payload = { value, savedAt: Date.now() };
    sessionStorage.setItem(LOCATION_CACHE_KEY, JSON.stringify(payload));
  }

  // RENDER VISITS - Updates the table with visitor history
  // Clears old rows and populates with current visit data
  function renderVisits(visits) {
    tableBody.innerHTML = '';
    visits.forEach((visit) => {
      const tr = document.createElement('tr');
      const timeCell = document.createElement('td');
      timeCell.textContent = visit.time;
      const locationCell = document.createElement('td');
      locationCell.textContent = visit.location;
      tr.appendChild(timeCell);
      tr.appendChild(locationCell);
      tableBody.appendChild(tr);
    });
  }

  // GET CURRENT LOCATION - Fetches location from multiple providers with fallback
  // Tries 3 different IP geolocation services to maximize success rate
  // Caches result to avoid repeated API calls
  async function getCurrentLocation() {
    // Check if we already have a cached location
    const cachedLocation = readCachedLocation();
    if (cachedLocation) return cachedLocation;

    // List of geolocation APIs to try (in order of preference)
    const providers = [
      {
        url: 'https://ipapi.co/json/',
        // Extract city and country from this provider's response format
        map: (data) => ({ city: data.city, country: data.country_name })
      },
      {
        url: 'https://ipwho.is/',
        map: (data) => ({ city: data.city, country: data.country })
      },
      {
        url: 'https://ipinfo.io/json',
        map: (data) => ({ city: data.city, country: data.country })
      }
    ];

    // Try each provider until one succeeds
    for (const provider of providers) {
      try {
        const response = await fetch(provider.url);
        if (!response.ok) continue;
        const data = await response.json();
        const mapped = provider.map(data);
        const city = mapped.city || 'Unknown city';
        const country = mapped.country || 'Unknown country';
        // Only return if we got meaningful data
        if (city !== 'Unknown city' || country !== 'Unknown country') {
          const locationText = `${city}, ${country}`;
          saveCachedLocation(locationText);
          return locationText;
        }
      } catch {
        // This provider failed, try the next one
      }
    }

    // All providers failed
    return 'Location unavailable';
  }

  // INIT PAGE - Main initialization function
  // 1. Sets greeting based on time of day
  // 2. Gets current location
  // 3. Stores visit in history
  // 4. Renders updated visit table
  async function initPage() {
    const now = new Date();
    greeting.textContent = `${getGreeting(now.getHours())} I'm Jalol, welcome to my webpage.`;

    // Get existing visit history
    const existingVisits = readVisits();
    // Fetch current location from IP geolocation
    const currentLocation = await getCurrentLocation();
    // Add current visit to top of list, keep only MAX_VISITS
    const updatedVisits = [
      { time: formatTime(now), location: currentLocation },
      ...existingVisits
    ].slice(0, MAX_VISITS);

    // Save updated history and render to page
    saveVisits(updatedVisits);
    renderVisits(updatedVisits);
  }

  // Start initialization
  initPage();
})();

// Form handling: simple client-side processing to satisfy "form can be sent"
(function formHandler(){
  const form = document.getElementById('contactForm');
  if(!form) return;
  form.addEventListener('submit', (e)=>{
    e.preventDefault();
    const data = new FormData(form);
    const payload = {};
    for(const [k,v] of data.entries()) payload[k]=v;
    // simulate processing: log and show friendly message
    console.log('Contact form submitted', payload);
    alert('Thanks! Your message has been received (simulated).');
    form.reset();
  });
})();
