from googleapiclient.discovery import build
import sys
from HTMLParser import HTMLParser
from math import ceil

htmlParser = HTMLParser()
service = build('translate', 'v2', developerKey='AIzaSyBDJdJCoHyh38AL1epAjHmQY9Fh-a2WT4I')

def areFileExtensionValid( filenames, extension ):
	errorMessage = 'File must have \'' + extension + '\' as file extension'

	extensionLength = len( extension )
	for filename in filenames:
		filenameLength = len( filename )

		if filenameLength <= extensionLength:
			print 'Error in \'' + filename + '\''
			print '\t' + errorMessage
			return False

		fileExtension = filename[ filenameLength - extensionLength: ]
		if fileExtension != extension:
			print 'Error in \'' + filename + '\''
			print '\t' + errorMessage
			return False
	return True

def readFile( filename ):
	data = {}
	data[ 'filename' ] = filename
	file = open( filename, 'r' )
	line = file.readline()
	tokens = line.split()
	data[ 'pairName' ] = tokens[ 1 ]

	data['speakers' ] = []
	line = file.readline()
	while line[0] == '-':
		tokens = line.split()
		data[ 'speakers' ].append( tokens[1] )
		line = file.readline()

	line = file.readline()
	line = file.readline()

	data[ 'statements' ] = []
	while line != '### END':
		statement = {}
		tokens = line.split()

		statement[ 'timestamp' ] = tokens[0][ 1:5 ]
		statement[ 'speaker' ] = tokens[1]

		line = file.readline()
		statement[ 'statement' ] = line
		data[ 'statements' ].append( statement )

		line = file.readline()
		line = file.readline()

	file.close()
	return data

def changeFileExtension( filename, newExtension ):
	dotIndex = filename.rfind( '.' )
	baseFilename = filename[ 0:dotIndex ]
	return baseFilename + newExtension

def gTranslate( lines ):
	queries = service.translations().list(
		source='tl',	# source is Filipino / Tagalog
		target='en',	# target is English
		q=lines			# lines to translate
	).execute()['translations']
	
	result = []
	for query in queries:
		result.append( htmlParser.unescape( query['translatedText'] ) )
	return result

def translateData( data, cap ):
	translated = []
	toTranslateLen = len( data[ 'statements' ] )
	querySliceSize = min( cap, toTranslateLen )
	iterationNumber = ceil( toTranslateLen / float( querySliceSize ) )
	for i in range(0, int( iterationNumber ) ):
		start = i * querySliceSize
		size = min( querySliceSize, toTranslateLen - start )
		end = start + size

		toTranslate = []
		for j in range(start, end):
			toTranslate.append( data[ 'statements' ][j][ 'statement' ] )

		translatedArray = gTranslate( toTranslate )
		translated = translated + translatedArray
	
	for i in range( 0, len( translated ) ):
		data[ 'statements' ][ i ][ 'translated' ] = translated[ i ]

def writeTranslation( data ):
	lines = []
	lines.append( '### ' + data[ 'pairName' ] )
	for speaker in data[ 'speakers' ]:
		lines.append( '- ' + speaker )
	lines.append( '' )
	lines.append( '### Transcription' )
	for instance in data[ 'statements' ]:
		timestamp = '[' + instance[ 'timestamp' ] + ']'
		lines.append( timestamp + ' ' + instance[ 'speaker' ] )
		lines.append( instance[ 'translated' ] )
		lines.append( '' )

	writePath = changeFileExtension( data[ 'filename' ], '.translate' )
	file = open( writePath, 'w' )
	for line in lines:
		file.write( line + '\n' ) 
	file.write('### END')
	file.close()

def main():
	args = sys.argv[1:]

	if len(args) == 0:
		print 'Usage: python translator.py fileA.transcribe[ fileB.transcribe[ ...]]'
		return
	
	valid = areFileExtensionValid( args, '.transcribe' )
	if not valid:
		return

	# process each file
	for readPath in args:
		print 'Processing ' + readPath + '...'
		print '\treading line-by-line'
		data = readFile( readPath )
		print '\ttranslating lines'
		translateData( data, 50 )
		print '\twriting translations to file'
		writeTranslation( data )

		print '   SUCCESS'
	
if __name__ == '__main__':
	main()