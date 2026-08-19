-- Seed 10 archetypes with translations in ru, en, kk

INSERT INTO gamefication.archetype (image, archetype_state) VALUES
    ('explorer.png', 'PUBLISHED'),
    ('adventurer.png', 'PUBLISHED'),
    ('wanderer.png', 'PUBLISHED'),
    ('foodie.png', 'PUBLISHED'),
    ('culture_lover.png', 'PUBLISHED'),
    ('photographer.png', 'PUBLISHED'),
    ('nomad.png', 'PUBLISHED'),
    ('relaxer.png', 'PUBLISHED'),
    ('thrill_seeker.png', 'PUBLISHED'),
    ('local_guide.png', 'PUBLISHED');

INSERT INTO gamefication.archetype_translation (archetype_id, name, description, language_code)
SELECT a.id, t.name, t.description, t.language_code
FROM (VALUES
    ('explorer.png', 'Исследователь', 'Всегда ищет новые места и маршруты, которые ещё никто не проходил.', 'ru'),
    ('explorer.png', 'Explorer', 'Always looking for new places and routes no one has taken yet.', 'en'),
    ('explorer.png', 'Зерттеуші', 'Әрдайым әлі ешкім бармаған жаңа орындар мен бағыттарды іздейді.', 'kk'),

    ('adventurer.png', 'Искатель приключений', 'Любит риск, экстрим и непредсказуемые путешествия.', 'ru'),
    ('adventurer.png', 'Adventurer', 'Loves risk, extreme activities and unpredictable trips.', 'en'),
    ('adventurer.png', 'Шытырман оқиға іздеуші', 'Тәуекелді, экстримді және болжанбайтын саяхаттарды жақсы көреді.', 'kk'),

    ('wanderer.png', 'Странник', 'Путешествует без чёткого плана, доверяясь моменту.', 'ru'),
    ('wanderer.png', 'Wanderer', 'Travels without a strict plan, trusting the moment.', 'en'),
    ('wanderer.png', 'Кезбе', 'Нақты жоспарсыз, сол сәтке сеніп саяхаттайды.', 'kk'),

    ('foodie.png', 'Гурман', 'Выбирает маршруты, ориентируясь на местную кухню.', 'ru'),
    ('foodie.png', 'Foodie', 'Chooses routes based on local cuisine.', 'en'),
    ('foodie.png', 'Дәмқор', 'Бағыттарды жергілікті асханаға қарай таңдайды.', 'kk'),

    ('culture_lover.png', 'Ценитель культуры', 'Интересуется музеями, историей и традициями мест.', 'ru'),
    ('culture_lover.png', 'Culture Lover', 'Interested in museums, history and local traditions.', 'en'),
    ('culture_lover.png', 'Мәдениет ғашығы', 'Мұражайларға, тарихқа және дәстүрлерге қызығушылық танытады.', 'kk'),

    ('photographer.png', 'Фотограф', 'Путешествует ради красивых кадров и уникальных видов.', 'ru'),
    ('photographer.png', 'Photographer', 'Travels for beautiful shots and unique views.', 'en'),
    ('photographer.png', 'Фотограф', 'Әдемі суреттер мен ерекше көріністер үшін саяхаттайды.', 'kk'),

    ('nomad.png', 'Кочевник', 'Постоянно в движении, не задерживается на одном месте надолго.', 'ru'),
    ('nomad.png', 'Nomad', 'Always on the move, never staying in one place for long.', 'en'),
    ('nomad.png', 'Көшпенді', 'Үнемі қозғалыста, бір жерде ұзақ қалмайды.', 'kk'),

    ('relaxer.png', 'Отдыхающий', 'Предпочитает спокойный отдых и размеренные маршруты.', 'ru'),
    ('relaxer.png', 'Relaxer', 'Prefers calm rest and slow-paced routes.', 'en'),
    ('relaxer.png', 'Демалушы', 'Тыныш демалыс пен байсалды бағыттарды жақсы көреді.', 'kk'),

    ('thrill_seeker.png', 'Любитель острых ощущений', 'Ищет адреналин: экстремальные виды спорта и активности.', 'ru'),
    ('thrill_seeker.png', 'Thrill Seeker', 'Seeks adrenaline through extreme sports and activities.', 'en'),
    ('thrill_seeker.png', 'Адреналин құмар', 'Экстремалды спорт пен белсенділік арқылы адреналин іздейді.', 'kk'),

    ('local_guide.png', 'Местный гид', 'Хорошо знает свой город и любит показывать его другим.', 'ru'),
    ('local_guide.png', 'Local Guide', 'Knows their city well and loves showing it to others.', 'en'),
    ('local_guide.png', 'Жергілікті гид', 'Өз қаласын жақсы біледі және оны басқаларға көрсеткенді ұнатады.', 'kk')
) AS t(image, name, description, language_code)
JOIN gamefication.archetype a ON a.image = t.image;
