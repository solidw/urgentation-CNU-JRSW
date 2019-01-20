from pyfcm import FCMNotification
server_key = 'AAAAk0BhIm0:APA91bGeSyPU7rtnZWxca7HROFaJXEahCT1WYMSsVk8_alV0dAH2NmnSY7TDQt6zmVoXSAkmerryyKIdmkiU8uJWRET9S0VQ4ShkOTV9_Hj7zOA8C-OgYLjyI6ZkmR8rH-tjO4rFeVAJ'
user_token = ' cBnWJmGmBIE:APA91bFPgW8KxQLnbIkPWfA4tt9wZ0ITM7nHqqqwFWP9BjgMebquDWkkGI-eaWhhvZNGHN9G0Qwl_3NiD_hid-Sp6cWh3RPgXFHWJju5riz2fV_Ytd6SXNyBzuNGI4OKkbk1zT6IWlHe'

push_service = FCMNotification(api_key=server_key)

result = push_service.notify_single_device(registration_id=user_token, message_title='testTitle_wantae', message_body='1호선 사고났다 사고 사고')
print('sendDone!')
