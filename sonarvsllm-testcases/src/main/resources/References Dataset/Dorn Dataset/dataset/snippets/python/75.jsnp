	'P' : '001010010',
	'Q' : '000000111',
	'R' : '100000110',
	'S' : '001000110',
	'T' : '000010110',
	'U' : '110000001',
	'V' : '011000001',
	'W' : '111000000',
	'X' : '010010001',
	'Y' : '110010000',
	'Z' : '011010000',
	'-' : '010000101',
	'*' : '010010100',
	'+' : '010001010',
	'$' : '010101000',
	'%' : '000101010',
	'/' : '010100010',
	'.' : '110000100',
	' ' : '011000100',
}

class Object(Barcode):
	# Convert a text into string binary of black and white markers
	def encode(self, text):
		text       = text.upper()
		self.label = text
		text       = '*' + text + '*'
		result     = ''
		# It isposible for us to encode code39
		# into full ascii, but this feature is
		# not enabled here
		for char in text:
			if not encoding.has_key(char):
				char = '-';

			result = result + encoding[char] + '0';

		# Now we need to encode the code39, best read
		# the code to understand what it's up to:
		encoded = '';
		colour   = '1'; # 1 = Black, 0 = White
		for data in result:
			if data == '1':
				encoded = encoded + colour + colour
			else:
				encoded = encoded + colour
			if colour == '1':
				colour = '0'
			else:
				colour = '1'
