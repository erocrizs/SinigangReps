from googleapiclient.discovery import build
import sys
from HTMLParser import HTMLParser
from math import ceil

htmlParser = HTMLParser()
service = build('translate', 'v2', developerKey='AIzaSyBDJdJCoHyh38AL1epAjHmQY9Fh-a2WT4I')

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
	
	
def main():
	args = sys.argv[1:]

	if len(args) == 0:
		print 'Usage: python translator.py fileA.transcribe[ fileB.transcribe[ ...]]'
		return
	
	inputFileExtensionLength = len( '.transcribe' )
	for arg in args:
		length = len(arg)
		if length <= inputFileExtensionLength or arg[ length - inputFileExtensionLength: ] != '.transcribe':
			print 'Error in \'' + arg + '\':'
			print '\tFile must have \'.transcribe\' as file extension'
			return
	
	# process each file
	for readPath in args:
		print 'Processing ' + readPath + '...'
		
		readPathLen = len(readPath)
		writePath = readPath[ :readPathLen - inputFileExtensionLength ] + '.translate'
		
		print '   opening file'
		readFile = open(readPath, 'r')
		writeFile = open(writePath, 'w')
		
		# copy the first few lines until the actual transcription
		print '   parsing line-by-line'
		line = ''
		while line != '### Transcription\n':
			line = readFile.readline()
			writeFile.write( line )
		
		# reading each line of the transcription
		lineSpeaker = []
		toTranslate = []
		line = readFile.readline()
		while line != '### END':
			lineSpeaker.append( line )
			line = readFile.readline() # for the current log
			toTranslate.append( line )
			line = readFile.readline() # for the succeeding empty line
			line = readFile.readline() # for the next line speaker
		
		# translating all logs
		print '   translating logs'
		gTranslated = []
		minSlice = 90
		querySliceSize = min( minSlice, len(toTranslate) )
		iterationNumber = ceil( len(toTranslate) / float( querySliceSize ) )
		for i in range(0, int( iterationNumber ) ):
			start = i * querySliceSize
			size = min( querySliceSize, len(toTranslate) - start )
			end = start + size
			print '      translating lines ' + str( start + 1 ) + ' to ' + str( end )
			translateArray = toTranslate[ start:end ]
			translatedArray = gTranslate( translateArray )
			gTranslated = gTranslated + translatedArray
			
		# writing translated logs in identical format as the original
		print '   writing to ' + writePath
		toWrite = ""
		for i in range(0, len(gTranslated)):
			toWrite += lineSpeaker[i] + '\t' + gTranslated[i] + '\n\n'
		toWrite += '### END'
		
		writeFile.write( toWrite )
		
		print '   closing files'
		readFile.close()
		writeFile.close()
		
		print '   SUCCESS'
	
if __name__ == '__main__':
	main()