package day17

import utils.readInput
import kotlin.math.pow

fun readInput(): Pair<MutableMap<String, Int>, List<Int>> {
    val register = Regex("Register (.): (\\d+)")
    val program = Regex("Program: (.+)")
    val input = readInput("17")
    val a = register.find(input[0])!!.groupValues[2].toInt()
    val b = register.find(input[1])!!.groupValues[2].toInt()
    val c = register.find(input[2])!!.groupValues[2].toInt()
    val p = program.find(input[4])!!.groupValues[1].split(",").map { it.toInt() }
    return mutableMapOf("a" to a, "b" to b, "c" to c) to p
}

fun run(registers: MutableMap<String, Int>, program: List<Int>): MutableList<Int> {
    fun oValue(operand: Int): Int {
        return when (operand) {
            in 0..3 -> operand
            4 -> registers["a"]!!
            5 -> registers["b"]!!
            6 -> registers["c"]!!
            else -> operand
        }
    }

    fun adv(operand: Int): Int {
        return (registers["a"]!! / 2.0.pow(oValue(operand))).toInt()
    }

    var instruction = 0
    val outputs = mutableListOf<Int>()
    while (instruction in program.indices) {
        val opcode = program[instruction]
        val operand = program[instruction + 1]
        when (opcode) {
            0 -> {
                registers["a"] = adv(operand)
            }

            1 -> {
                registers["b"] = registers["b"]!! xor operand
            }

            2 -> {
                registers["b"] = oValue(operand) % 8
            }


            3 -> {
                if (registers["a"] != 0) {
                    instruction = operand
                    continue
                }
            }

            4 -> {
                registers["b"] = registers["b"]!! xor registers["c"]!!
            }

            5 -> {
                outputs.add(oValue(operand) % 8)
            }

            6 -> {
                registers["b"] = adv(operand)
            }

            7 -> {
                registers["c"] = adv(operand)
            }
        }

        instruction += 2
    }

    return outputs
}


fun main() {
    val (registers, program) = readInput()
    val outputs = run(registers, program)
    println(outputs.joinToString(","))
}