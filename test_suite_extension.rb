#!/usr/bin/rubyne
# A script to run the tests for the extension
#
require "open3"

TESTS_FOR_LOOP = `find ./extension_tests/for`
TESTS_DOWHILE_LOOP = `find ./extension_tests/doWhile`
TESTS_VALID
TESTS_INVALID_SEMANTIC
EMULATE = `arm-linux-gnueabi-gcc -o exe -mcpu=arm1176jzf-s -mtune=arm1176jzf-s
           ticTacToe.s  && qemu-arm -L /usr/arm-linux-gnueabi/exe`


puts "Testing extension..."

if ARGV[0] == nil
    run_test()
elsif ARGV[0] == "valid"
    run_test(TESTS_VALID, "Valid", 1)
elsif ARGV[0] == "semantic"
    run_test(TESTS_INVALID_SEMANTIC, "Semantic", 200)
elsif ARGV[0] == "syntax"
    run_test()
