(function () {
  function getByIds(ids) {
    for (var i = 0; i < ids.length; i += 1) {
      var el = document.getElementById(ids[i]);
      if (el) return el;
    }
    return null;
  }

  function initContrastToggle() {
    var button = getByIds(['contrast-toggle', 'contrastToggle']);
    if (!button) return;

    var body = document.body;
    var storageKey = 'ji-high-contrast';

    function apply(isOn) {
      body.classList.toggle('high-contrast', isOn);
      button.setAttribute('aria-pressed', String(isOn));
      button.textContent = isOn ? '◑ Normal' : '◑ Contrast';
      localStorage.setItem(storageKey, String(isOn));
    }

    apply(localStorage.getItem(storageKey) === 'true');

    button.addEventListener('click', function () {
      apply(!body.classList.contains('high-contrast'));
    });
  }

  function initThemeToggle() {
    var button = document.getElementById('themeToggle');
    if (!button) return;

    var body = document.body;
    var storageKey = 'ji-theme-dark';

    function apply(isOn) {
      body.classList.toggle('theme-dark', isOn);
      button.setAttribute('aria-pressed', String(isOn));
      button.querySelector('.theme-icon').textContent = isOn ? '☀' : '🌙';
      localStorage.setItem(storageKey, String(isOn));
    }

    apply(localStorage.getItem(storageKey) === 'true');

    button.addEventListener('click', function () {
      apply(!body.classList.contains('theme-dark'));
    });
  }

  function initFontControls() {
    var increaseBtn = getByIds(['font-increase']);
    var decreaseBtn = getByIds(['font-decrease']);
    if (!increaseBtn || !decreaseBtn) return;

    var storageKey = 'ji-font-size';
    var currentSize = parseFloat(localStorage.getItem(storageKey) || '100');
    var MIN = 80;
    var MAX = 140;
    var STEP = 10;

    function applySize(size) {
      document.documentElement.style.fontSize = size + '%';
      localStorage.setItem(storageKey, String(size));
      increaseBtn.disabled = size >= MAX;
      decreaseBtn.disabled = size <= MIN;
    }

    applySize(currentSize);

    increaseBtn.addEventListener('click', function () {
      if (currentSize < MAX) {
        currentSize += STEP;
        applySize(currentSize);
      }
    });

    decreaseBtn.addEventListener('click', function () {
      if (currentSize > MIN) {
        currentSize -= STEP;
        applySize(currentSize);
      }
    });
  }

  function initMobileNav() {
    var toggle = document.getElementById('nav-toggle');
    var nav = document.getElementById('primary-nav');
    if (!toggle || !nav) return;

    toggle.addEventListener('click', function () {
      var isOpen = nav.classList.toggle('nav-open');
      toggle.setAttribute('aria-expanded', String(isOpen));
      toggle.textContent = isOpen ? '✕' : '☰';
    });

    var links = nav.querySelectorAll('a');
    for (var i = 0; i < links.length; i += 1) {
      links[i].addEventListener('click', function () {
        nav.classList.remove('nav-open');
        toggle.setAttribute('aria-expanded', 'false');
        toggle.textContent = '☰';
      });
    }
  }

  function initSkillBars() {
    var fills = document.querySelectorAll('.skill-bar-fill');
    if (!fills.length || typeof IntersectionObserver === 'undefined') return;

    for (var i = 0; i < fills.length; i += 1) {
      fills[i].dataset.target = fills[i].style.width;
      fills[i].style.width = '0%';
    }

    var observer = new IntersectionObserver(function (entries) {
      for (var j = 0; j < entries.length; j += 1) {
        if (entries[j].isIntersecting) {
          var fill = entries[j].target;
          fill.style.width = fill.dataset.target;
          observer.unobserve(fill);
        }
      }
    }, { threshold: 0.3 });

    for (var k = 0; k < fills.length; k += 1) {
      observer.observe(fills[k]);
    }
  }

  function init() {
    initThemeToggle();
    initContrastToggle();
    initFontControls();
    initMobileNav();
    initSkillBars();
  }

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', init);
  } else {
    init();
  }
}());