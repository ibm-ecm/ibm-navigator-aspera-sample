/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "URL של שרת IBM Aspera",
		configuration_pane_aspera_url_hover: "ציינו את ה-URL של שרת IBM Aspera.‏ לדוגמה: https://host_name:port_number/aspera/faspex",
		configuration_pane_aspera_url_prompt: "מומלץ מאוד להשתמש בפרוטוקול HTTPS.",
		configuration_pane_max_docs_to_send: "מספר מרבי של פריטים לשליחה",
		configuration_pane_max_docs_to_send_hover: "ציינו את מספר הפריטים המרבי שמשתמשים יכולים לשלוח בבת אחת. ",
		configuration_pane_max_procs_to_send: "מספר מקסימלי של בקשות מקבילות",
		configuration_pane_max_procs_to_send_hover: "ציינו את מספר הבקשות המרבי שניתן יהיה להפעיל בעת ובעונה אחת. ",
		configuration_pane_target_transfer_rate: "קצב העברת היעד (ב-MB לשנייה)",
		configuration_pane_target_transfer_rate_hover: "ציין את קצב העברץ היעד במגה-סיביות לשנייה. הקצב מוגבל לפי הרישיון.",
		configuration_pane_speed_info: "הרישיון הבסיסי הנוכחי שלכם הוא ל-20 MB לשנייה.שדרגו לרישיון הערכה מהיר יותר (עד 10 GB לשנייה) של Aspera Faspex בדף <a target='_blank' href='https://ibm.biz/BdjYHq'>בקשת הערכת Aspera</a>.",

		// runtime
		send_dialog_sender_title: "שולח: ${0}",
		send_dialog_not_set: "לא מוגדר",
		send_dialog_send_one: "שלחו '${0}'.",
		send_dialog_send_more: "שלחו ${0} קבצים.",
		send_dialog_sender: "שם משתמש:",
		send_dialog_password: "סיסמה:",
		send_dialog_missing_sender_message: "עליכם לציין שם משתמש כדי להתחבר אל שרת IBM Aspera.",
		send_dialog_missing_password_message: "עליכם לציין סיסמה כדי להתחבר אל שרת IBM Aspera.",
		send_dialog_title: "שליחה באמצעות IBM Aspera",
		send_dialog_missing_title_message: "יש לציין כותרת.",
		send_dialog_info: "שליחת קבצים דרך שרת IBM Aspera ודיווח למשתמשים שהקבצים זמינים להורדה.",
		send_dialog_recipients_label: "נמענים:",
		send_dialog_recipients_textfield_hover_help: "השתמשו בפסיקים להפרדת כתובות דואל ו/או שמות משתמשים. לדוגמה, ציינו  'address1, ‏address2, ‏username1, ‏username2'.",
		send_dialog_missing_recipients_message: "יש לציין כתובת דואל אחת או שם משתמש אחד.",
		send_dialog_title_label: "כותרת:",
		send_dialog_note_label: "הוספת הודעה.",
		send_dialog_earPassphrase_label: "סיסמת הצפנה:",
		send_dialog_earPassphrase_textfield_hover_help: "הזינו סיסמה כדי להצפין את הקבצים בשרת. כתוצאה מכך, נמענים יידרשו להזין את הסיסמה כדי לפענח את הקבצים המוגנים כשהם יורדו.",
		send_dialog_notify_title: "דיווח: ${0}",
		send_dialog_notifyOnUpload_label: "להודיע לי כאשר הקובץ נטען",
		send_dialog_notifyOnDownload_label: "הודיעו לי כאשר הקובץ מורד",
		send_dialog_notifyOnUploadDownload: "להודיע לי כאשר הקובץ נטען ומורד",
		send_dialog_send_button_label: "משלוח",
		send_dialog_started: "החבילה ${0} נשלחת כעת.",
		status_started: "מצב חבילה: ${0} - בתהליך (${1}%)",
		status_stopped: "מצב חבילה: ${0} - מופסק",
		status_failed: "מצב חבילה: ${0} - נכשל",
		status_completed: "מצב חבילה: ${0} - הושלם",

		// error
		send_dialog_too_many_items_error: "לא ניתן לשלוח את הפריטים.",
		send_dialog_too_many_items_error_explanation: "תוכלו לשלוח עד ${0} פריטים בכל פעם. אתם מנסים לשלוח ${1} פריטים.",
		send_dialog_too_many_items_error_userResponse: "בחרו פחות פריטים ונסו לשלוח את הפריטים שוב. תוכלו גם לפנות למנהלן המערכת כדי להגדיל את מספר הפריטים המרבי שתוכלו לשלוח בבת אחת.",
		send_dialog_too_many_items_error_0: "maximum_number_of_items",
		send_dialog_too_many_items_error_1: "number_of_items",
		send_dialog_too_many_items_error_number: 5050,
});

