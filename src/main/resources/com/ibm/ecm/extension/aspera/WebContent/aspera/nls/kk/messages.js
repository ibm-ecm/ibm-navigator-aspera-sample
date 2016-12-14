/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "IBM Aspera сервер URL мекен-жайы",
		configuration_pane_aspera_url_hover: "IBM Aspera серверінің URL мекен-жайын енгізіңіз. Мысалы: https://хост_атауы:порт_саны/aspera/faspex",
		configuration_pane_aspera_url_prompt: "HTTPS протоколын пайдалану ұсынылады.",
		configuration_pane_max_docs_to_send: "Жіберетін элементтердің ең көп саны",
		configuration_pane_max_docs_to_send_hover: "Пайдаланушылар бір уақытта жібере алатын элементтердің ең үлкен санын көрсетіңіз.",
		configuration_pane_max_procs_to_send: "Бірлескен сұраулардың максималды саны",
		configuration_pane_max_procs_to_send_hover: "Бір уақытта іске қосылатын сұраулардың ең көп санын көрсетіңіз.",
		configuration_pane_target_transfer_rate: "Мақсатты беру жылдамдығы (Мбит/с) ",
		configuration_pane_target_transfer_rate_hover: "Мегабитке секундына мақсатты тасымалдау жылдамдығын көрсетіңіз. Бұл тариф лицензия бойынша шектеледі.",
		configuration_pane_speed_info: "Сіздің ағымдағы деңгейдегі лицензияңыз 20 Мбит/с. Aspera Faspex үшін <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Бағалау Сұранысы</a> бетіне сұрау арқылы жылдам бағалау лицензиясын (10 Гбит/с дейін) жаңартыңыз.",

		// runtime
		send_dialog_sender_title: "Жіберуші: ${0}",
		send_dialog_not_set: "Орнатылмаған",
		send_dialog_send_one: "'${0}' жіберіңіз.",
		send_dialog_send_more: "${0} файлды жіберу.",
		send_dialog_sender: "Пайдаланушы аты:",
		send_dialog_password: "Құпия сөз:",
		send_dialog_missing_sender_message: "IBM Aspera серверіне кіру үшін пайдаланушы атын енгізу керек.",
		send_dialog_missing_password_message: "IBM Aspera серверіне кіру үшін құпия сөзді енгізу керек.",
		send_dialog_title: "IBM Aspera арқылы жіберу",
		send_dialog_missing_title_message: "Тақырыпты енгізуіңіз керек.",
		send_dialog_info: "Файлдарды IBM Aspera сервері арқылы жіберіңіз және пайдаланушыларға қотаруға болатын файлдар туралы хабарлаңыз.",
		send_dialog_recipients_label: "Алушылар:",
		send_dialog_recipients_textfield_hover_help: "Электрондық пошта мекенжайларын және / немесе пайдаланушының аттарын бөлектеу үшін үтір белгісін пайдаланыңыз. Мысалы, ' мекн-жайы1, мекн-жайы2, пайдаланушы аты 1, пайдаланушы аты 2' енгізіңіз.",
		send_dialog_missing_recipients_message: "Кемінде бір электрондық пошта мекенжайын немесе пайдаланушы атын көрсетуіңіз керек.",
		send_dialog_title_label: "Тақырып:",
		send_dialog_note_label: "Хабарлама қосу.",
		send_dialog_earPassphrase_label: "Шифрлау құпия сөзі:",
		send_dialog_earPassphrase_textfield_hover_help: "Сервердегі файлдарды шифрлау үшін құпия сөзді енгізіңіз. Содан кейін, алушылар қорғалған файлдарды шифрлау үшін парольді жүктеліп жатқан кезде енгізуі керек.",
		send_dialog_notify_title: "Хабарландыру: ${0}",
		send_dialog_notifyOnUpload_label: "Файл жүктеліп салынған соң маған хабарлаңыз",
		send_dialog_notifyOnDownload_label: "Файл жүктеліп алынған соң маған хабарлаңыз",
		send_dialog_notifyOnUploadDownload: "Файл жүктеліп салынған және жүктеліп алынған соң маған хабарлаңыз",
		send_dialog_send_button_label: "Жіберу",
		send_dialog_started: "${0} бумасы жіберілуде.",
		status_started: "Бума күйі: ${0} - Орындалуда (${1}%)",
		status_stopped: "Бума күйі: ${0} - Тоқтатылды",
		status_failed: "Бума күйі: ${0} - Сәтсіз аяқталды",
		status_completed: "Бума күйі: ${0} - Аяқталды",

		// error
		send_dialog_too_many_items_error: "Элементтерді жіберу мүмкін емес.",
		send_dialog_too_many_items_error_explanation: "Сіз бір уақытта ${0} элементке дейін жібере аласыз. ${1} элементтерді жіберуге тырысудасыз.",
		send_dialog_too_many_items_error_userResponse: "Бірнеше элементтерді таңдап және элементтерді қайтадан жіберуге тырысыңыз. Сондай-ақ, бір уақытта жіберуге болатын элементтердің ең көп санына көбейту үшін жүйе әкімшісімен байланысуға болады.",
		send_dialog_too_many_items_error_0: "maximum_number_of_items",
		send_dialog_too_many_items_error_1: "number_of_items",
		send_dialog_too_many_items_error_number: 5050,
});

