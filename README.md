# ✅ To-Do List App 📝  

**To-Do List App** – это удобное мобильное приложение для управления задачами, разработанное на **Kotlin + Jetpack Compose**. Поддерживает создание, редактирование, удаление, дедлайны и фильтрацию задач.  

---

## 🚀 **Функциональность**
✅ **Добавление задач** ➕  
✅ **Редактирование задач** ✏️  
✅ **Удаление задач свайпом** 🗑️  
✅ **Установка дедлайна с помощью `DatePickerDialog`** 📅  
✅ **Фильтрация задач ("Все", "С дедлайном", "Без дедлайна")** 📌  
✅ **Локальное сохранение с помощью `Room Database`** 🗄️  
✅ **Поддержка темной/светлой темы** 🌙☀️  
✅ **Полноценное тестирование (JUnit + Espresso + Compose Testing)** ✅  

---

## 🔧 **Используемые технологии**  
📌 **Язык**: `Kotlin`  
📌 **Архитектура**: `MVVM (Model-View-ViewModel)`  
📌 **UI**: `Jetpack Compose`  
📌 **База данных**: `Room`  
📌 **Навигация**: `Jetpack Navigation`  
📌 **Диалоги и UI-компоненты**: `Material3`  
📌 **Тестирование**:  
   - `JUnit` (юнит-тесты ViewModel и базы данных)  
   - `Espresso` (инструментальные тесты UI)  
   - `Compose UI Testing` (тесты для Jetpack Compose)  

---

## 📂 **Структура проекта**
```
📂 app/src/main
 ├── 📂 java/com/example/todoapp
 │   ├── 📂 data  # Данные
 │   │   ├── 📂 local  # Room Database
 │   │   │   ├── ToDo.kt  # Модель задачи
 │   │   │   ├── ToDoDao.kt  # DAO для работы с базой
 │   │   │   ├── ToDoDatabase.kt  # Инициализация базы
 │   ├── 📂 ui  # UI-экран
 │   │   ├── ToDoScreen.kt  # Главный экран приложения
 │   │   ├── ToDoItem.kt  # Отдельный элемент списка
 │   ├── 📂 viewmodel  # ViewModel для работы с задачами
 │   │   ├── ToDoViewModel.kt  # ViewModel для управления данными
 ├── 📂 androidTest/java/com/example/todoapp  # UI-тесты (Espresso)
 ├── 📂 test/java/com/example/todoapp  # Юнит-тесты (JUnit)
```

---

## 🔧 **Как запустить проект**
1️⃣ **Клонировать репозиторий**  
```sh
git clone https://github.com/ТВОЙ_GITHUB/ToDoApp.git
```
2️⃣ **Открыть проект в `Android Studio`**  
3️⃣ **Синхронизировать зависимости** (`Sync Project with Gradle Files`)  
4️⃣ **Запустить приложение** (`Run ▶`)  

---

## 🔬 **Как запустить тесты**
📌 **Запуск юнит-тестов (ViewModel, Room):**  
```sh
./gradlew test
```
📌 **Запуск инструментальных тестов UI (`Espresso` и `Compose`):**  
```sh
./gradlew connectedAndroidTest
```

---

## 📌 **Планы на будущее**
🚀 **Добавить push-уведомления перед дедлайном**  
🚀 **Синхронизацию с Firebase**  
🚀 **Авторизацию через Google**  

---

🔥 **Разработчик:** [ТВОЙ_GITHUB](https://github.com/ТВОЙ_GITHUB)  
😺 **Если понравилось – ставь ⭐ и форкай проект!**

