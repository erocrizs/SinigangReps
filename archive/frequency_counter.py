import sys
import os

def main():
	args = sys.argv[1:]

	if len(args) == 0:
		print 'Usage: python translator.py fileA.transcribe[ fileB.transcribe[ ...]]'
		return
	
	inputFileExtensionLength = len( '.translate' )
	for arg in args:
		length = len(arg)
		if length < inputFileExtensionLength or arg[ length - inputFileExtensionLength: ] != '.translate':
			print 'Error in \'' + arg + '\':'
			print '\tFile must have \'.translate\' as file extension'
			return
	
	# process each file
	for readPath in args:
		print 'Processing ' + readPath + '...'
		
		readPathLen = len(readPath)
		frequencyWritePath = readPath[ :readPathLen - inputFileExtensionLength ] + '.frequency'
		excludePath = 'exclude.in'

		print '   opening file'
		readFile = open( readPath, 'r' )
		writeFrequencyFile = open( frequencyWritePath, 'w' )

		# reading excluded words
		print '   reading exclude file'
		exclude = set([])
		if os.path.isdir( excludePath ):
			with open( excludePath, 'r+' ) as excludeFile:
				excludeWords = excludeFile.read().splitlines()
				exclude = set(excludeWords)
				print '      ' + str( exclude )

		# ignore the first few lines until the actual transcription
		print '   parsing line-by-line'
		line = ''
		while line != '### Transcription\n':
			line = readFile.readline()

		frequency = {}

		# reading each line of the transcription
		toTranslate = []
		line = readFile.readline()
		while line != '### END':
			line = readFile.readline() # for the current log
			
			words = line.split()
			for word in words:
				word = word.lower()

				start = 0
				end = len( word ) - 1
				while not word[start].isalnum():
					start += 1
				while not word[end].isalnum():
					end -= 1

				if start < end:
					word = word[ start:end+1 ]
					if not word in exclude:
						frequency[ word ] = frequency.get( word, 0 ) + 1

			line = readFile.readline() # for the succeeding empty line
			line = readFile.readline() # for the next line speaker

		# determine which keywords will be kept
		print '   determine if each keyword should be considered a topic'
		keywords = frequency.keys()
		excludeFile = open( excludePath, 'a+' )
		toWriteFrequency = ''
		toWriteExclude = ''
		for keyword in keywords:
			resolved = False
			while not resolved:
				print '      \'' + keyword + '\' [y/n]? '
				response = raw_input().lower()
				if response == 'y':
					resolved = True
				elif response == 'n':
					toRecord = keyword + '\n'
					excludeFile.write( toRecord )
					del frequency[ keyword ]
					resolved = True
				else:
					print '         invalid response'

		# merging keywords together
		print '   merging counts of synonyms'
		# TODO

		# writing keywords into file
		print '   writing to ' + frequencyWritePath
		keywords = frequency.keys()
		for keyword in keywords:
			toRecord = keyword + ' : ' + str( frequency[ keyword ] ) + '\n'
			writeFrequencyFile.write()

		print '   closing files'
		readFile.close()
		writeFrequencyFile.close()
		excludeFile.close()

		print '   SUCCESS'

if __name__ == '__main__':
	main()