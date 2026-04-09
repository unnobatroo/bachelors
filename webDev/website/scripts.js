// Shared navigation builder to keep links consistent across pages
(function buildNav() {
  const nav = document.querySelector('.sidenav');
  if (!nav) return;

  const items = [
    { href: 'index.html', label: 'Home' },
    { href: 'blog.html', label: 'Blog' },
    { href: 'favs.html', label: 'Favs' }
  ];

  // Keep the raw HTML feel: plain anchors, default colors
  nav.innerHTML = items
    .map((i) => `<a href="${i.href}">${i.label}</a>`) 
    .join('');
})();

// Utilities and homepage-only logic below
(function homePageInit(){
  const greeting = document.getElementById('greeting');
  const tableBody = document.getElementById('locationLog');

  // If these elements are not present, this isn't the homepage; skip.
  if (!greeting || !tableBody) return;

  const STORAGE_KEY = 'last5Visits';
  const MAX_VISITS = 5;
  const LOCATION_CACHE_KEY = 'cachedLocation';
  const LOCATION_CACHE_TTL_MS = 10 * 60 * 1000;

  // One formatter for all visit timestamps so format stays consistent.
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

  function getGreeting(hour) {
    if (hour >= 5 && hour < 12) return 'Good morning!';
    if (hour >= 12 && hour < 18) return 'Good afternoon!';
    if (hour >= 18 && hour < 22) return 'Good evening!';
    return 'Good night!';
  }

  function formatTime(date) {
    return visitTimeFormatter.format(date).replace(',', '');
  }

  function readVisits() {
    try {
      const data = JSON.parse(localStorage.getItem(STORAGE_KEY));
      return Array.isArray(data) ? data : [];
    } catch {
      return [];
    }
  }

  function saveVisits(visits) {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(visits));
  }

  function readCachedLocation() {
    try {
      const cached = JSON.parse(sessionStorage.getItem(LOCATION_CACHE_KEY));
      if (!cached) return null;
      const isExpired = Date.now() - cached.savedAt > LOCATION_CACHE_TTL_MS;
      return isExpired ? null : cached.value;
    } catch {
      return null;
    }
  }

  function saveCachedLocation(value) {
    const payload = { value, savedAt: Date.now() };
    sessionStorage.setItem(LOCATION_CACHE_KEY, JSON.stringify(payload));
  }

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

  async function getCurrentLocation() {
    const cachedLocation = readCachedLocation();
    if (cachedLocation) return cachedLocation;

    const providers = [
      {
        url: 'https://ipapi.co/json/',
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

    for (const provider of providers) {
      try {
        const response = await fetch(provider.url);
        if (!response.ok) continue;
        const data = await response.json();
        const mapped = provider.map(data);
        const city = mapped.city || 'Unknown city';
        const country = mapped.country || 'Unknown country';
        if (city !== 'Unknown city' || country !== 'Unknown country') {
          const locationText = `${city}, ${country}`;
          saveCachedLocation(locationText);
          return locationText;
        }
      } catch {
        // Ignore and try next provider.
      }
    }

    return 'Location unavailable';
  }

  async function initPage() {
    const now = new Date();
    greeting.textContent = `${getGreeting(now.getHours())} I'm Jalol, welcome to my webpage.`;

    const existingVisits = readVisits();
    const currentLocation = await getCurrentLocation();
    const updatedVisits = [
      { time: formatTime(now), location: currentLocation },
      ...existingVisits
    ].slice(0, MAX_VISITS);

    saveVisits(updatedVisits);
    renderVisits(updatedVisits);
  }

  initPage();
})();
