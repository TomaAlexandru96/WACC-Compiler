#!/usr/bin/ruby
# A script to emulate the program nad compare outputs for differen tests
require "open3"
TESTS = `find ./extension_tests/valid`

def execute(program_s)
    `arm-linux-gnueabi-gcc -o exe -mcpu=arm1176jzf-s -mtune=arm1176jzf-s
    #{program_s} && qemu-arm -L /usr/arm-linux-gnueabi/exe > output.out`
end

class Fail
    attr_reader :test

    def initialize test
        @test = test
    end

end

def emulate_tests(tests)
    puts "Emulating #{tests} ..."
    count = 1
    failed = 0
    failedTests = []
    tests.each_line do |test|
        if ((!File.directory? test) && (test.end_with? ".wacc"))
            printf("HHHHHHHHHHHHHHHHH")
            expected_file = (File.dirname test).chomp("wacc").concat("out")
            `./compile #{test}`
            test1 = test.chomp
            assembly_file = test1.chomp("wacc").concat("s")
            execute(ASSEMBLY_FILE)
            correct = Open3.capture2(`diff output.out #{expected_file} | wc -c`)
            count += 1
            if (correct != 0)
               failedTests[failed] = Fail.new test 
               failed += 1
            end
            `rm output.out && rm exe`
        end
    end
    if failed != 0
        puts "FAILED------------------------------------------------------------
        ------------------------------------------------------------------------
        ------------------------------------------------------------------------
        -----------------------------------------------------------------------"
    end
    failedTests.each do |failedTest|
        puts failedTest.test
    end
    #puts "#{name} : #{count - failed}/#{count}"
end

puts "Making ..."
`make`
puts "Emulating the test programs ..." 
emulate_tests(TESTS)
