package com.example.viktorina.data

object QuizRepository {
    fun getQuizQuestions(): List<Question> = listOf(
        Question(
            id = 1,
            question = "Какой инструмент добывает алмазную руду быстрее всего?",
            options = listOf("Деревянная кирка", "Железная кирка", "Рука", "Незеритовая кирка"),
            correctAnswerIndex = 3
        ),
        Question(
            id = 2,
            question = "Какой из этих мобов НЕ появляется в Незере (Нижнем мире)?",
            options = listOf("Ифрит", "Скелет-иссушитель", "Эндермен", "Зомби"),
            correctAnswerIndex = 3
        ),
        Question(
            id = 3,
            question = "Что нужно добавить в зелье, чтобы оно стало взрывным?",
            options = listOf("Светокамень", "Огненный порошок", "Порох", "Редстоун"),
            correctAnswerIndex = 2
        ),
        Question(
            id = 4,
            question = "Из какого материала НЕЛЬЗЯ создать меч?",
            options = listOf("Алмаз", "Дерево", "Кварц", "Золото"),
            correctAnswerIndex = 2
        ),
        Question(
            id = 5,
            question = "Как называется достижение за первое посещение Нижнего мира?",
            options = listOf("Огненные недра", "Путешественник по глубинам", "В горящую хижину", "Холодные ноги"),
            correctAnswerIndex = 0
        ),
        Question(
            id = 6,
            question = "Сколькими ударами железной кирки нужно добыть булыжник?",
            options = listOf("1", "3", "5", "10"),
            correctAnswerIndex = 2
        ),
        Question(
            id = 7,
            question = "Какое максимальное количество сердец может иметь игрок без эффектов?",
            options = listOf("10", "15", "20", "30"),
            correctAnswerIndex = 0
        ),
        Question(
            id = 8,
            question = "Какой из этих мобов самый быстрый?",
            options = listOf("Лошадь", "Лама", "Свинья под седлом с морковкой на удочке", "Страйдер"),
            correctAnswerIndex = 0
        ),
        Question(
            id = 9,
            question = "Какой эффект даёт игроку поедание ядовитого картофеля?",
            options = listOf("Отравление", "Голод", "Регенерация", "Он может не дать эффекта, а может отравить"),
            correctAnswerIndex = 3
        ),
        Question(
            id = 10,
            question = "Что происходит с крипером, если в него ударит молния?",
            options = listOf("Он становится гигантским крипером", "Он становится заряженным", "Он меняет цвет на синий", "Он лечится"),
            correctAnswerIndex = 1
        )
    )
}