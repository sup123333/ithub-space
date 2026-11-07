    const pages=['home','events','faculties','media','partners','portfolio','buildings','admission','profile','login','register'];
    const loaded={};
    function go(p){
      pages.forEach(x=>{
        document.getElementById('page-'+x).classList.remove('active');
        const n=document.getElementById('nav-'+x);if(n)n.classList.remove('active');
      });
      document.getElementById('page-'+p).classList.add('active');
      const n=document.getElementById('nav-'+p);if(n)n.classList.add('active');
      window.scrollTo({top:0,behavior:'instant'});
      if(!loaded[p]){loaded[p]=true;loadPage(p);}
      if(p==='buildings' && allBuildings.length===0){
        document.getElementById('buildings-list').innerHTML='<div class="loading"><div class="spinner"></div>Загрузка...</div>';
        loadBuildings();
      }
    }
    async function api(url){
      const token=localStorage.getItem('token');
      const h={'Content-Type':'application/json'};
      if(token)h['Authorization']='Bearer '+token;
      const r=await fetch(url,{headers:h});
      if(!r.ok)throw new Error('HTTP '+r.status);
      return r.json();
    }
    async function loadPage(p){
      try{
        if(p==='home')await loadHome();
        else if(p==='events')await loadEvents();
        else if(p==='faculties')await loadFaculties();
        else if(p==='media')await loadMedia();
        else if(p==='partners')await loadPartners();
        else if(p==='portfolio')await loadPortfolio();
        else if(p==='buildings')await loadBuildings();
        else if(p==='profile')await loadProfile();
      }catch(e){console.error('loadPage',p,e);}
    }
    async function loadHome(){
      renderTeachersScroller();
      const[ev,fa,pa,po]=await Promise.allSettled([api('/api/events'),api('/api/faculties'),api('/api/partners'),api('/api/portfolios')]);
      const e=ev.status==='fulfilled'?ev.value:[];
      const f=fa.status==='fulfilled'?fa.value:[];
      const p=pa.status==='fulfilled'?pa.value:[];
      const q=po.status==='fulfilled'?po.value:[];
      document.getElementById('stat-events').textContent=e.length||'—';
      document.getElementById('stat-faculties').textContent=f.length||'—';
      document.getElementById('stat-partners').textContent=p.length||'—';
      document.getElementById('stat-portfolio').textContent=q.length||'—';
      document.getElementById('home-events').innerHTML=e.slice(0,3).map(x=>renderEventSmall(x)).join('')||emptyH('Нет мероприятий');
      document.getElementById('home-faculties').innerHTML=f.slice(0,3).map((x,i)=>renderFacultySmall(x,i)).join('')||emptyH('Нет направлений');
    }
    async function loadEvents(){
      const d=await api('/api/events');
      const el=document.getElementById('events-list');
      if(!d.length){el.innerHTML=emptyH('Нет мероприятий');return;}
      el.innerHTML='<div style="display:grid;gap:20px">'+d.map(e=>renderEvent(e)).join('')+'</div>';
    }
    function renderEvent(e){
      const d=e.eventDate?new Date(e.eventDate):null;
      const day=d?d.getDate():'—';
      const month=d?d.toLocaleString('ru',{month:'long'}):'';
      const time=d?d.toLocaleTimeString('ru',{hour:'2-digit',minute:'2-digit'}):'';
      const year=d?d.getFullYear():'';
      const prog=e.program?`<div class="event-program"><div class="event-program-title">Программа</div>${esc(e.program)}</div>`:'';
      return`<div class="event-card">
        <div class="event-date-banner">
          <div><div class="event-date-big">${day}</div></div>
          <div><div class="event-date-month">${month} ${year}</div><div class="event-date-detail">${time?'Начало в '+time:'Время уточняется'}</div></div>
        </div>
        <div class="event-body">
          <h3>${esc(e.title)}</h3>
          <p>${esc(e.description||'')}</p>
          ${e.location?`<div class="event-location">${esc(e.location)}</div>`:''}
          ${prog}
        </div>
      </div>`;
    }
    function renderEventSmall(e){
      const d=e.eventDate?new Date(e.eventDate):null;
      const date=d?d.toLocaleDateString('ru',{day:'numeric',month:'short'}):'';
      return`<div class="card" onclick="go('events')" style="cursor:pointer">
        <div class="card-tag tag-event">Мероприятие</div>
        <h3>${esc(e.title)}</h3>
        <p>${esc((e.description||'').slice(0,100))}${(e.description||'').length>100?'...':''}</p>
        <div class="card-meta">${date?`<span>${date}</span>`:''}${e.location?`<span>${esc(e.location.split(',')[0])}</span>`:''}</div>
      </div>`;
    }
    let allFaculties=[];
    async function loadFaculties(){
      const d=await api('/api/faculties');
      allFaculties=d;
      const el=document.getElementById('faculties-list');
      if(!d.length){el.innerHTML=emptyH('Нет направлений');return;}
      el.innerHTML=d.map((f,i)=>renderFaculty(f,i)).join('');
    }
    function renderFaculty(f,i){
      return`<div class="faculty-card" onclick="openFaculty(${i})">
        <div class="faculty-num">0${i+1}</div>
        <h3>${esc(f.name)}</h3>
        <p>${esc(f.description||'')}</p>
        <div class="faculty-footer">
          <div class="faculty-head">Куратор<strong>${esc(f.headName||'Не указан')}</strong></div>
          <div class="faculty-count">${f.studentCount||'—'}<small>студентов</small></div>
        </div>
      </div>`;
    }
    let currentFacultyName=null;
    function openFaculty(i){
      const f=allFaculties[i];
      if(!f)return;
      currentFacultyName=f.name;
      document.getElementById('fm-title').textContent=f.name;
      document.getElementById('fm-desc').textContent=f.description||'';
      const roles=(f.businessRoles||'').split('·').map(s=>s.trim()).filter(Boolean);
      document.getElementById('fm-roles-block').style.display=roles.length?'':'none';
      document.getElementById('fm-roles').innerHTML=roles.map(r=>`<span class="fm-role-chip">${esc(r)}</span>`).join('');
      const skills=(f.skills||'').split('·').map(s=>s.trim()).filter(Boolean);
      document.getElementById('fm-skills-block').style.display=skills.length?'':'none';
      document.getElementById('fm-skills').innerHTML=skills.map(s=>`<div class="fm-skill-item"><span class="fm-skill-check">✓</span><span>${esc(s)}</span></div>`).join('');
      const curatorEl=document.getElementById('fm-curator');
      if(f.headName){
        const avatar=f.curatorPhoto
          ?`<img class="fm-curator-avatar" src="${esc(f.curatorPhoto)}" alt="${esc(f.headName)}">`
          :`<div class="fm-curator-avatar">${esc(f.headName[0])}</div>`;
        curatorEl.style.display='flex';
        curatorEl.innerHTML=`${avatar}<div class="fm-curator-info"><div class="fm-curator-role">Куратор направления</div><div class="fm-curator-name">${esc(f.headName)}</div></div>`;
      } else {
        curatorEl.style.display='none';
      }
      document.getElementById('faculty-modal').classList.add('open');
      document.body.style.overflow='hidden';
    }
    function closeFaculty(){
      document.getElementById('faculty-modal').classList.remove('open');
      document.body.style.overflow='';
    }
    function goToAdmissionWithDirection(dirName){
      closeFaculty();
      go('admission');
      if(!dirName)return;
      const btns=document.querySelectorAll('.adm-dir-btn');
      btns.forEach(b=>b.classList.remove('active'));
      const target=Array.from(btns).find(b=>b.textContent.trim().endsWith(dirName));
      if(target)target.classList.add('active');
      const sel=document.getElementById('adm-direction');
      if(sel)sel.value=dirName;
      setTimeout(()=>document.getElementById('adm-directions')?.scrollIntoView({behavior:'smooth',block:'center'}),150);
    }
    function renderFacultySmall(f,i){
      return`<div class="card" onclick="go('faculties')" style="cursor:pointer">
        <div style="font-size:11px;font-weight:700;color:var(--purple);margin-bottom:8px;text-transform:uppercase;letter-spacing:1px">Направление</div>
        <h3>${esc(f.name)}</h3>
        <p>${esc((f.description||'').slice(0,80))}...</p>
        <div class="card-meta">${f.studentCount?`<span>${f.studentCount} студентов</span>`:''}</div>
      </div>`;
    }
    let allMedia=[];
    async function loadMedia(){
      allMedia=await api('/api/media');
      renderMedia('ALL');
    }
    function filterMedia(type,btn){
      document.querySelectorAll('.filter-btn').forEach(b=>b.classList.remove('active'));
      btn.classList.add('active');
      renderMedia(type);
    }
    function renderMedia(type){
      const el=document.getElementById('media-list');
      const f=type==='ALL'?allMedia:allMedia.filter(m=>m.type===type);
      if(!f.length){el.innerHTML=emptyH('Нет медиафайлов');return;}
      el.innerHTML=f.map(m=>renderMediaCard(m)).join('');
    }
function renderMediaCard(m){
  const tagClass=m.type==='VIDEO'?'tag-video':m.type==='SPACE'?'tag-space':'tag-photo';
  const tagLabel=m.type==='VIDEO'?'Видео':m.type==='SPACE'?'Пространство':'Фото';
  const d=m.createdAt?new Date(m.createdAt).toLocaleDateString('ru'):'';
  const thumb=m.thumbnailUrl||m.url||'';
  const imgHtml=thumb
    ?`<img src="${esc(thumb)}" alt="${esc(m.title||'')}" style="width:100%;height:100%;object-fit:cover;border-radius:12px 12px 0 0">`
    :`<span class="media-type-badge ${tagClass}">${tagLabel}</span>`;
  return`<div class="media-card">
    <div class="media-thumb" style="background:none;overflow:hidden">${imgHtml}</div>
    <div class="media-body">
      <span class="media-type-badge ${tagClass}" style="margin-bottom:6px;display:inline-block">${tagLabel}</span>
      <h4>${esc(m.title||'Без названия')}</h4>
      ${m.description?`<p>${esc(m.description)}</p>`:''}
      ${d?`<p style="font-size:11px;color:var(--muted2)">${d}</p>`:''}
    </div>
  </div>`;
}
    async function loadPartners(){
      const d=await api('/api/partners');
      const el=document.getElementById('partners-list');
      if(!d.length){el.innerHTML=emptyH('Нет партнёров');return;}
      el.innerHTML=d.map(p=>renderPartner(p)).join('');
    }
    function renderPartner(p){
      const init=(p.companyName||'P')[0].toUpperCase();
      const c=[];
      if(p.email)c.push(esc(p.email));
      if(p.phone)c.push(esc(p.phone));
      if(p.contactPerson)c.push(esc(p.contactPerson));
      return`<div class="partner-card">
        <div class="partner-avatar">${init}</div>
        <h3>${esc(p.companyName)}</h3>
        <p>${esc(p.description||'')}</p>
        ${c.length?`<div class="partner-contacts">${c.map(x=>`<span>${x}</span>`).join('')}</div>`:''}
      </div>`;
    }
    async function loadPortfolio(){
      const d=await api('/api/portfolios');
      const el=document.getElementById('portfolio-list');
      if(!d.length){el.innerHTML=emptyH('Нет проектов');return;}
      el.innerHTML=d.map(p=>renderPortfolio(p)).join('');
    }
    function renderPortfolio(p){
      const link=p.projectUrl?`<a class="card-link" href="${esc(p.projectUrl)}" target="_blank" rel="noopener">Открыть проект →</a>`:'';
      return`<div class="portfolio-card">
        <div class="card-tag tag-portfolio">Проект</div>
        <h3>${esc(p.title)}</h3>
        <p>${esc(p.description||'')}</p>
        ${link}
      </div>`;
    }
    let allBuildings=[];
    const CITY_ORDER=['Ростов-на-Дону','Москва','Санкт-Петербург','Новосибирск','Владивосток','Нижний Новгород','Махачкала','Нальчик','Ставрополь','Тула','Магас','Камчатка'];
    // Маппинг синонимов для поиска по городу
    const CITY_ALIASES={
      'москва':'Москва','moscow':'Москва',
      'ростов':'Ростов-на-Дону','ростов-на-дону':'Ростов-на-Дону','rostov':'Ростов-на-Дону',
      'питер':'Санкт-Петербург','спб':'Санкт-Петербург','санкт-петербург':'Санкт-Петербург','петербург':'Санкт-Петербург','spb':'Санкт-Петербург',
      'новосибирск':'Новосибирск','нск':'Новосибирск',
      'владивосток':'Владивосток',
      'нижний':'Нижний Новгород','нижний новгород':'Нижний Новгород','нн':'Нижний Новгород',
      'махачкала':'Махачкала',
      'нальчик':'Нальчик',
      'ставрополь':'Ставрополь',
      'тула':'Тула',
      'магас':'Магас','назрань':'Магас',
      'камчатка':'Камчатка','петропавловск':'Камчатка',
    };
    function cityFromBuilding(b){
      const addr=(b.address||'').toLowerCase();
      const name=(b.name||'').toLowerCase();
      for(const c of CITY_ORDER){
        const cl=c.toLowerCase();
        if(addr.startsWith(cl)||addr.includes(cl+',')||name.includes(cl))return c;
      }
      // Москва — доп. проверка через "москва" в адресе
      if(addr.includes('москва')||name.includes('москва'))return 'Москва';
      return (b.address||'').split(',')[0].trim()||'Другие';
    }
    async function loadBuildings(){
      allBuildings=await api('/api/buildings');
      renderBuildings(allBuildings);
    }
    async function searchBuildings(q){
      if(!q.trim()){renderBuildings(allBuildings);return;}
      const lq=q.toLowerCase().trim();
      // Проверяем: не является ли запрос названием города?
      const cityMatch=CITY_ALIASES[lq];
      let filtered;
      if(cityMatch){
        // фильтр только по городу
        filtered=allBuildings.filter(b=>cityFromBuilding(b)===cityMatch);
      } else {
        // общий поиск по названию, адресу, описанию
        filtered=allBuildings.filter(b=>
          (b.name||'').toLowerCase().includes(lq)||
          (b.address||'').toLowerCase().includes(lq)||
          (b.description||'').toLowerCase().includes(lq)
        );
      }
      renderBuildings(filtered, !cityMatch);
    }
    function renderBuildings(data,flat=false){
      const el=document.getElementById('buildings-list');
      if(!data||!data.length){el.innerHTML=emptyH('Ничего не найдено','Попробуйте другой запрос');return;}
      if(flat){
        el.style.display='grid';
        el.style.gridTemplateColumns='repeat(auto-fill,minmax(300px,1fr))';
        el.innerHTML=data.map(buildingCard).join('');
        return;
      }
      el.style.display='flex';
      el.style.flexDirection='column';
      const groups={};
      for(const b of data){
        const city=cityFromBuilding(b);
        if(!groups[city])groups[city]=[];
        groups[city].push(b);
      }
      const cityKeys=Object.keys(groups).sort((a,b)=>{
        if(a==='Ростов-на-Дону')return -1;if(b==='Ростов-на-Дону')return 1;
        if(a==='Москва')return -1;if(b==='Москва')return 1;
        return a.localeCompare(b,'ru');
      });
      const defaultOpen=new Set(['Ростов-на-Дону','Москва']);
      el.innerHTML=cityKeys.map(city=>{
        const cnt=groups[city].length;
        return`<div class="buildings-city-group" data-city="${esc(city)}">
          <div class="buildings-city-header" onclick="toggleCity(this)">
            <span class="buildings-city-dot"></span>
            <h4 class="buildings-city-name">${esc(city)}</h4>
            <span class="buildings-city-count">${cnt} ${cnt===1?'корпус':cnt<5?'корпуса':'корпусов'}</span>
            <span class="buildings-city-chevron">▼</span>
          </div>
          <div class="buildings-city-body">
            <div class="buildings-city-inner">
              <div class="grid-auto" style="padding-top:12px">${groups[city].map(buildingCard).join('')}</div>
            </div>
          </div>
        </div>`;
      }).join('');
      // двойной rAF: первый кадр — браузер рисует закрытые блоки,
      // второй — добавляем open и CSS transition плавно раскрывает
      requestAnimationFrame(()=>requestAnimationFrame(()=>{
        el.querySelectorAll('.buildings-city-group').forEach(g=>{
          if(defaultOpen.has(g.dataset.city)) g.classList.add('open');
        });
      }));
    }
    function toggleCity(header){
      header.closest('.buildings-city-group').classList.toggle('open');
    }
    function buildingCard(b){
      return`<div class="card">
        <div class="card-tag tag-building">Корпус</div>
        <h3>${esc(b.name)}</h3>
        <p>${esc(b.description||'')}</p>
        <div class="card-meta"><span>${esc(b.address||'')}</span></div>
        ${b.mapUrl?`<a class="card-link" href="${esc(b.mapUrl)}" target="_blank" rel="noopener">Открыть на карте →</a>`:''}
      </div>`;
    }
    async function loadProfile(){
      const el=document.getElementById('profile-content');
      const token=localStorage.getItem('token');
      if(!token){
        el.innerHTML=`<div class="empty"><h3>Войдите в аккаунт</h3><p>Для просмотра профиля необходима авторизация</p><br><button class="btn btn-primary" onclick="go('login')" style="margin-top:16px">Войти</button></div>`;
        return;
      }
      try{
        const d=await api('/api/users/me');
        const labels={ADMIN:'Администратор',TEACHER:'Преподаватель',STUDENT:'Студент',APPLICANT:'Абитуриент',PARTNER:'Партнёр'};
        const init=((d.firstName||'')[0]+(d.lastName||'')[0]).toUpperCase()||'U';
        const fields=[
          {l:'Имя',v:[d.firstName,d.middleName,d.lastName].filter(Boolean).join(' ')},
          {l:'Email',v:d.email},
          {l:'Роль',v:`<span class="role-badge role-${d.role}">${labels[d.role]||d.role}</span>`},
          {l:'Город',v:d.city},{l:'Направление',v:d.direction},
          {l:'Группа',v:d.groupName},{l:'Предмет',v:d.subject},
        ].filter(f=>f.v);
        el.innerHTML=`<div class="profile-card">
          <div class="profile-avatar">${init}</div>
          <div class="profile-name">${esc([d.firstName,d.lastName].filter(Boolean).join(' '))}</div>
          <div class="profile-email">${esc(d.email||'')}</div>
          ${fields.map(f=>`<div class="profile-field"><label>${f.l}</label><span>${typeof f.v==='string'?esc(f.v):f.v}</span></div>`).join('')}
        </div>`;
      }catch{
        el.innerHTML=`<div class="empty"><h3>Ошибка загрузки</h3><p>Не удалось получить данные профиля</p></div>`;
      }
    }
    async function doLogin(){
      const email=document.getElementById('login-email').value.trim();
      const pass=document.getElementById('login-password').value;
      if(!email||!pass){showAlert('login-error','Заполните все поля');return;}
      try{
        const r=await fetch('/api/auth/login',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({email,password:pass})});
        if(!r.ok){showAlert('login-error','Неверный email или пароль');return;}
        const token=await r.text();
        localStorage.setItem('token',token);
        updateAuthUI(true,email);
        go('home');
      }catch{showAlert('login-error','Ошибка соединения с сервером');}
    }
    async function doRegister(){
      const firstName=document.getElementById('reg-first').value.trim();
      const lastName=document.getElementById('reg-last').value.trim();
      const middleName=document.getElementById('reg-middle').value.trim();
      const email=document.getElementById('reg-email').value.trim();
      const password=document.getElementById('reg-pass').value;
      const city=document.getElementById('reg-city').value.trim();
      const direction=document.getElementById('reg-direction').value;
      if(!firstName||!lastName||!email||!password){showAlert('reg-error','Заполните обязательные поля');return;}
      try{
        const r=await fetch('/api/auth/register',{method:'POST',headers:{'Content-Type':'application/json'},
          body:JSON.stringify({firstName,lastName,middleName,email,password,city,direction,role:'STUDENT'})});
        if(!r.ok){showAlert('reg-error','Ошибка — возможно, email уже занят');return;}
        showAlert('reg-ok','✓ Аккаунт создан! Выполняется вход...');
        setTimeout(()=>go('login'),1500);
      }catch{showAlert('reg-error','Ошибка соединения с сервером');}
    }
    function logout(){localStorage.removeItem('token');updateAuthUI(false);go('home');}
    function updateAuthUI(on,email){
      document.getElementById('nav-auth').style.display=on?'none':'flex';
      document.getElementById('nav-user').style.display=on?'flex':'none';
      document.getElementById('mob-auth').style.display=on?'none':'flex';
      document.getElementById('mob-user').style.display=on?'flex':'none';
      if(email)document.getElementById('nav-username').textContent=email.split('@')[0];
    }
    function esc(s){if(!s)return'';return String(s).replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;');}
    function emptyH(t,s){return`<div class="empty"><h3>${t}</h3>${s?`<p>${s}</p>`:''}</div>`;}
    function showAlert(id,msg){const el=document.getElementById(id);el.textContent=msg;el.style.display='block';setTimeout(()=>el.style.display='none',4000);}
    const TEACHERS=[
      {name:'Мария Кузнецова',fullName:'Мария Петровна Кузнецова',role:'Директор IThub Ростов',subject:'Руководитель проектов',dir:'Директор',photo:'/images/teachers/kuznetsova.png'},
      {name:'Мария Фудулей',role:'Преподаватель',subject:'Введение в программирование',dir:'Программирование',photo:'/images/teachers/fuduley.png'},
      {name:'Мария Москвина',fullName:'Мария Николаевна Москвина',role:'Преподаватель',subject:'Английский язык',dir:'Английский язык',photo:'/images/teachers/moskvina.png'},
      {name:'Виктория Колесникова',fullName:'Виктория Юрьевна Колесникова',role:'Заместитель директора',subject:'Методическая работа · Цифровой маркетинг',dir:'Зам. директора по методике',photo:'/images/teachers/kolesnikova.png'},
      {name:'Екатерина Пархоменко',fullName:'Екатерина Сергеевна Пархоменко',role:'Заместитель директора',subject:'Организация учебного процесса',dir:'Зам. директора',photo:'/images/teachers/parkhomenko.png'},
      {name:'Вероника Инаркаева',role:'Преподаватель',subject:'Основные принципы UX/UI дизайна',dir:'UX/UI дизайн',photo:'/images/teachers/inarkayeva.png'},
      {name:'Иван Сериков',role:'Преподаватель',subject:'HTML / CSS · Веб-разработка',dir:'HTML / CSS',photo:'/images/teachers/serikov.png'},
      {name:'Айшат Юсупова',role:'Преподаватель',subject:'СУБД · PostgreSQL · MySQL',dir:'СУБД · PostgreSQL',photo:'/images/teachers/yusupova.png'},
      {name:'Елена Дозорова',role:'Преподаватель',subject:'Управление веб-серверами · Кибербезопасность',dir:'Кибербезопасность',photo:'/images/teachers/dozorova.png'},
      {name:'Анастасия Климашева',role:'Преподаватель',subject:'Английский язык',dir:'Английский язык',photo:'/images/teachers/klimasheva.png'},
      {name:'Николай Русланович',role:'Преподаватель',subject:'Физическая культура',dir:'Физическая культура',photo:'/images/teachers/ruslanovich.png'},
      {name:'Татьяна Шульга',role:'Преподаватель',subject:'Высшая математика',dir:'Высшая математика',photo:'/images/teachers/shulga.png'},
      {name:'Светлана Зинякова',role:'Преподаватель',subject:'Английский язык',dir:'Английский язык',photo:'/images/teachers/zinyakova.png'},
      {name:'Эльмира Типаева',role:'Преподаватель',subject:'Математическая логика',dir:'Математическая логика',photo:'/images/teachers/tipayeva.png'},
      {name:'Марта Просветкина',role:'Преподаватель',subject:'Литература',dir:'Литература',photo:'/images/teachers/prosvetkina.png'},
      {name:'Мариетта Дадян',role:'Преподаватель',subject:'История',dir:'История',photo:'/images/teachers/dadyan.png'},
      {name:'Светлана Межерицкая',role:'Преподаватель',subject:'Английский язык',dir:'Английский язык',photo:'/images/teachers/mezheritskaya.png'},
      {name:'Константин Поляков',role:'Преподаватель',subject:'Английский язык',dir:'Английский язык',photo:'/images/teachers/polyakov.png'},
      {name:'Ирина Шеравнер',role:'Преподаватель',subject:'Английский язык',dir:'Английский язык',photo:'/images/teachers/sheravner.png'},
      {name:'Дарья Пидченко',role:'Преподаватель',subject:'Английский язык',dir:'Английский язык',photo:'/images/teachers/pidchenko.png'},
      {name:'Михаил Сумбатян',role:'Основатель IThub',subject:'Создатель колледжа',dir:'Основатель IThub',photo:'/images/teachers/sumbatyan.png'},
    ];
    const TEACHER_BGS=[
      'linear-gradient(160deg,rgba(151,71,255,.25),rgba(170,1,233,.15))',
      'linear-gradient(160deg,rgba(251,191,36,.18),rgba(151,71,255,.12))',
      'linear-gradient(160deg,rgba(151,71,255,.18),rgba(236,72,153,.1))',
      'linear-gradient(160deg,rgba(79,142,247,.2),rgba(151,71,255,.12))',
      'linear-gradient(160deg,rgba(236,72,153,.18),rgba(151,71,255,.12))',
      'linear-gradient(160deg,rgba(76,194,241,.18),rgba(151,71,255,.12))',
      'linear-gradient(160deg,rgba(151,71,255,.2),rgba(170,1,233,.12))',
      'linear-gradient(160deg,rgba(170,1,233,.2),rgba(251,191,36,.08))',
      'linear-gradient(160deg,rgba(34,197,94,.18),rgba(151,71,255,.12))',
      'linear-gradient(160deg,rgba(79,142,247,.18),rgba(170,1,233,.12))',
      'linear-gradient(160deg,rgba(151,71,255,.18),rgba(76,194,241,.12))',
      'linear-gradient(160deg,rgba(236,72,153,.15),rgba(251,191,36,.08))',
      'linear-gradient(160deg,rgba(34,197,94,.15),rgba(151,71,255,.12))',
      'linear-gradient(160deg,rgba(170,1,233,.18),rgba(76,194,241,.08))',
      'linear-gradient(160deg,rgba(151,71,255,.2),rgba(236,72,153,.08))',
      'linear-gradient(160deg,rgba(79,142,247,.15),rgba(170,1,233,.12))',
      'linear-gradient(160deg,rgba(251,191,36,.12),rgba(151,71,255,.12))',
      'linear-gradient(160deg,rgba(76,194,241,.15),rgba(151,71,255,.12))',
      'linear-gradient(160deg,rgba(236,72,153,.15),rgba(170,1,233,.12))',
      'linear-gradient(160deg,rgba(34,197,94,.12),rgba(79,142,247,.12))',
      'linear-gradient(160deg,rgba(251,191,36,.2),rgba(170,1,233,.12))',
    ];
    function renderTeachersScroller(){
      const track=document.getElementById('teachers-track');
      if(!track)return;
      track.innerHTML=TEACHERS.map((t,i)=>`
        <div class="student-card" onclick="openTeacher(${i})">
          <div class="student-photo" style="background:${TEACHER_BGS[i]||TEACHER_BGS[0]}">
            ${t.photo?`<img class="student-photo-img" src="${t.photo}" alt="${esc(t.name)}" loading="lazy" onerror="this.remove()">`:''}
            <div class="student-photo-letter">${esc(t.name[0])}</div>
            <div class="student-photo-overlay"><div class="student-name">${esc(t.name)}</div><div class="student-dir">${esc(t.dir||t.subject)}</div></div>
          </div>
        </div>`).join('');
    }
    function openTeacher(i){
      const t=TEACHERS[i];
      document.getElementById('tm-photo-bg').style.background = TEACHER_BGS[i] || TEACHER_BGS[0];
      document.getElementById('tm-photo-letter').textContent = t.name[0];
      document.getElementById('tm-name').textContent = t.fullName || t.name;
      // tag: директор / основатель особый
      const tag = document.querySelector('.tm-tag');
      const roleLc = t.role.toLowerCase();
      tag.textContent = (roleLc.includes('директор') || roleLc.includes('основатель')) ? 'Руководство' : 'Преподаватель';
      document.getElementById('tm-role').textContent = t.role;
      document.getElementById('tm-subject').textContent = t.subject;
      // meta
      document.getElementById('tm-meta').innerHTML = `
        <div class="tm-meta-item"><div class="tm-meta-label">Предмет / направление</div><div class="tm-meta-value">${esc(t.subject)}</div></div>
        <div class="tm-meta-item"><div class="tm-meta-label">Должность</div><div class="tm-meta-value">${esc(t.role)}</div></div>
      `;
      // photo
      const img = document.getElementById('tm-photo-img');
      img.classList.remove('loaded');
      if(t.photo){img.src=t.photo;img.onload=()=>img.classList.add('loaded');}
      document.getElementById('teacher-modal').classList.add('open');
      document.body.style.overflow='hidden';
    }
    function closeTeacher(){
      document.getElementById('teacher-modal').classList.remove('open');
      document.body.style.overflow='';
    }
    document.addEventListener('keydown',e=>{if(e.key==='Escape'){closeTeacher();closeFaculty();}});
    function scrollTeachers(dir){
      const t=document.getElementById('teachers-track');
      t.scrollBy({left:dir*260,behavior:'smooth'});
    }
    // ── Theme ──
    function toggleTheme(){
      const isLight=document.documentElement.dataset.theme==='light';
      const next=isLight?'dark':'light';
      applyTheme(next);
      localStorage.setItem('theme',next);
    }
    function applyTheme(t){
      const isLight=t==='light';
      document.documentElement.dataset.theme=isLight?'light':'';
      const icon=isLight?'☀️':'🌙';
      const label=isLight?'Светлая тема':'Тёмная тема';
      ['theme-btn','theme-btn2'].forEach(id=>{
        const el=document.getElementById(id);
        if(el)el.textContent=icon;
      });
      const mi=document.getElementById('theme-mob-icon');
      const ml=document.getElementById('theme-mob-label');
      if(mi)mi.textContent=icon;
      if(ml)ml.textContent=label;
    }
    // ── FAQ ──
    function toggleFaq(qEl) {
      const item = qEl.closest('.faq-item');
      const isOpen = item.classList.contains('open');
      // закрываем все
      document.querySelectorAll('.faq-item.open').forEach(el => el.classList.remove('open'));
      // открываем кликнутый если был закрыт
      if (!isOpen) item.classList.add('open');
    }
    // ── Admission ──
    function selectDir(btn, dir) {
      document.querySelectorAll('.adm-dir-btn').forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      const sel = document.getElementById('adm-direction');
      if(sel) sel.value = dir;
    }
    async function submitAdmission() {
      const first = document.getElementById('adm-first').value.trim();
      const last  = document.getElementById('adm-last').value.trim();
      const email = document.getElementById('adm-email').value.trim();
      const dir   = document.getElementById('adm-direction').value;
      const phone = document.getElementById('adm-phone').value.trim();
      const grade = document.getElementById('adm-grade').value;
      const comment = document.getElementById('adm-comment').value.trim();
      if(!first||!last||!email||!dir){
        showAlert('adm-error','Заполните обязательные поля: имя, фамилия, email и направление');return;
      }
      // Регистрируем как абитуриента через существующий API
      try{
        const r = await fetch('/api/auth/register',{
          method:'POST', headers:{'Content-Type':'application/json'},
          body: JSON.stringify({
            firstName:first, lastName:last, email, password: Math.random().toString(36).slice(2)+'Aa1!',
            city:'Ростов-на-Дону', direction:dir, role:'APPLICANT',
            middleName: [grade, phone, comment].filter(Boolean).join(' | ')
          })
        });
        if(r.ok){
          showAlert('adm-ok','✓ Заявка отправлена! Приёмная комиссия свяжется с вами в течение 1 рабочего дня.');
          ['adm-first','adm-last','adm-email','adm-phone','adm-comment'].forEach(id=>{document.getElementById(id).value='';});
          document.getElementById('adm-direction').value='';
          document.getElementById('adm-grade').value='';
        } else if(r.status===409||r.status===400){
          showAlert('adm-error','Заявка с таким email уже существует. Мы с вами свяжемся!');
        } else {
          showAlert('adm-error','Ошибка отправки. Позвоните нам: +7 (863) 306-20-19');
        }
      }catch{
        showAlert('adm-error','Нет соединения с сервером. Позвоните: +7 (863) 306-20-19');
      }
    }
    async function submitExcursion(){
      const name = document.getElementById('exc-name').value.trim();
      const phone = document.getElementById('exc-phone').value.trim();
      const email = document.getElementById('exc-email').value.trim();
      if(!name||!phone){
        showAlert('exc-error','Заполните имя и телефон');return;
      }
      try{
        const r = await fetch('/api/excursions',{
          method:'POST', headers:{'Content-Type':'application/json'},
          body: JSON.stringify({name, phone, email: email||null})
        });
        if(r.ok){
          showAlert('exc-ok','✓ Заявка принята! Мы свяжемся с вами, чтобы договориться о времени.');
          ['exc-name','exc-phone','exc-email'].forEach(id=>{document.getElementById(id).value='';});
        } else {
          showAlert('exc-error','Ошибка отправки. Позвоните нам: +7 (863) 306-20-19');
        }
      }catch{
        showAlert('exc-error','Нет соединения с сервером. Позвоните: +7 (863) 306-20-19');
      }
    }
    // ── Mobile nav ──
    function toggleMobileNav(){
      const burger=document.getElementById('nav-burger');
      const drawer=document.getElementById('nav-drawer');
      const isOpen=drawer.classList.toggle('open');
      burger.classList.toggle('open',isOpen);
      document.body.style.overflow=isOpen?'hidden':'';
    }
    function closeMobileNav(){
      document.getElementById('nav-burger').classList.remove('open');
      document.getElementById('nav-drawer').classList.remove('open');
      document.body.style.overflow='';
    }
    // Закрыть drawer при клике вне
    document.addEventListener('click',e=>{
      const drawer=document.getElementById('nav-drawer');
      const burger=document.getElementById('nav-burger');
      if(drawer.classList.contains('open')&&!drawer.contains(e.target)&&!burger.contains(e.target))
        closeMobileNav();
    });
    (function(){
      // тема
      const savedTheme=localStorage.getItem('theme')||'dark';
      applyTheme(savedTheme);
      // auth
      const token=localStorage.getItem('token');
      if(token){try{const p=JSON.parse(atob(token.split('.')[1]));updateAuthUI(true,p.sub||'');}catch{updateAuthUI(true);}}
      loadPage('home');
    })();
