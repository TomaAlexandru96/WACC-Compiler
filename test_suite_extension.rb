#!/usr/bin/ruby
# A script to run the tests for the extension

require "open3"

#TESTS_FOR_LOOP = `find ./extension_tests/for`
#TESTS_DOWHILE_LOOP = `find ./extension_tests/doWhile`

TESTS_VALID  = `find ./extension_tests/valid`
TESTS_INVALID_SYNATX = `find ./extension_tests/invalid/syntaxErr`
TESTS_INVALID_SEMANTIC = `find ./extension_tests/invalid/semanticErr`

EMULATE = `arm-linux-gnueabi-gcc -o exe -mcpu=arm1176jzf-s -mtune=arm1176jzf-s
           ticTacToe.s  && qemu-arm -L /usr/arm-linux-gnueabi/exe`


class Fail
    attr_reader :test
    attr_reader :error_message

    def initialize test, error_message
        @test = test
        @error_message = error_message
    end
end

def run_test(tests, name, correct_exitstatus)
    puts "Testing #{name} ..."
    count = 1
    failed = 0
    failedTests = []
    tests.each_line do |test|
        test = test.chomp
        if ((!File.directory? test) && (test.end_with? ".wacc"))
            __, stderr, op = Open3.capture3("./compile #{test}")
            puts "#{count} - #{test} : #{op.exitstatus}"
            puts stderr
            count += 1
            if (op.exitstatus != correct_exitstatus)
                failedTests[failed] = Fail.new test, stderr;
                failed += 1
            end
        end
    end
    if failed != 0
        puts "FAILED ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
    end

    failedTests.each do |failedTest|
        puts failedTest.test
        puts failedTest.error_message
    end
    puts "#{name}: #{count - failed}/#{count}"
end

puts "Making ..."

`make`

puts "Teststing extension..."

if ARGV[0] == nil
  run_test(TESTS_VALID, "Valid", 0)
  run_test(TESTS_INVALID_SEMANTIC, "Semantic", 200)
  run_test(TESTS_INVALID_SYNATX, "Syntax", 100)
elsif ARGV[0] == "valid"
  run_test(TESTS_VALID, "Valid", 0)
elsif ARGV[0] == "semantic"
  run_test(TESTS_INVALID_SEMANTIC, "Semantic", 200)
elsif ARGV[0] == "syntax"
  run_test(TESTS_INVALID_SYNATX, "Syntax", 100)
else
  puts "Invalid argument"
end

puts "Finished"
