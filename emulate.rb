#!/usr/bin/ruby
# A script to emulate the program nad compare outputs for differen tests
require "open3"
TESTS = `find ./extension_tests/valid`

def execute(program_s)
    command = `arm-linux-gnueabi-gcc -o exe -mcpu=arm1176jzf-s -mtune=arm1176jzf-s #{program_s} && qemu-arm -L /usr/arm-linux-gnueabi exe > output.out`
end

class Fail
    attr_reader :test

    def initialize test
        @test = test
    end

end

def emulate_tests(tests)
    count = 1
    failed = 0
    failedTests = []
    tests.each_line do |test|
        test = test.chomp
        if ((!File.directory? test) && (test.end_with? ".wacc"))
            `./compile #{test}`
            assembly_file = test.split('/').last.chomp("wacc").concat("s")
            execute(assembly_file)
            correct = `diff #{test.chomp("wacc").concat("out")} output.out | wc -c`
            count += 1
            if (correct.to_i != 0)
               failedTests[failed] = Fail.new test 
               failed += 1
            end
            `rm output.out && rm exe`
        end
    end
    if failed != 0
        puts "FAILED------------------------------------------------------------
        -----------------------------------------------------------------------"
    end
    failedTests.each do |failedTest|
        puts failedTest.test
    end
    puts "#{count - failed}/#{count}"
end

puts "Making ..."
`make`
puts "Emulating the test programs ..." 
emulate_tests(TESTS)
