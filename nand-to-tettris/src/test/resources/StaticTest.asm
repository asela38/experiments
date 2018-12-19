	@256
	D=A
	@SP
	M=D

// call for Sys.init 
	@t$ret.0
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@LCL
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@ARG
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THIS
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THAT
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@5
	D=A
	@SP
	D=M-D
	@ARG
	M=D
	@SP
	D=M
	@LCL
	M=D
	@Sys.init
	0;JMP
(t$ret.0)

// File Name: Class1.vm 

// function Class1.set 0 
(Class1.set)

// push argument 0 
	@0 // -- addr = LCL + 0
	D=A
	@ARG
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// pop static 0 
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@t.Class1.vm.0
	M=D

// push argument 1 
	@1 // -- addr = LCL + 1
	D=A
	@ARG
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// pop static 1 
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@t.Class1.vm.1
	M=D

// push constant 0 
	@0 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// return 

// return 
	@LCL
	D=M
	@endFrame
	M=D
	@5
	A=D-A
	D=M
	@retAddr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@ARG
	A=M
	M=D
	D=A+1
	@SP
	M=D
	@4
	D=A
	@endFrame
	A=M-D
	D=M
	@LCL
	M=D
	@3
	D=A
	@endFrame
	A=M-D
	D=M
	@ARG
	M=D
	@2
	D=A
	@endFrame
	A=M-D
	D=M
	@THIS
	M=D
	@1
	D=A
	@endFrame
	A=M-D
	D=M
	@THAT
	M=D
	@retAddr
	A=M
	0;JMP

// function Class1.get 0 
(Class1.get)

// push static 0 
	@t.Class1.vm.0 // *sp = 0
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// push static 1 
	@t.Class1.vm.1 // *sp = 1
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// sub 
	@SP
	M=M-1
	A=M
	D=M
	A=A-1
	M=D-M
	M=-M

// return 

// return 
	@LCL
	D=M
	@endFrame
	M=D
	@5
	A=D-A
	D=M
	@retAddr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@ARG
	A=M
	M=D
	D=A+1
	@SP
	M=D
	@4
	D=A
	@endFrame
	A=M-D
	D=M
	@LCL
	M=D
	@3
	D=A
	@endFrame
	A=M-D
	D=M
	@ARG
	M=D
	@2
	D=A
	@endFrame
	A=M-D
	D=M
	@THIS
	M=D
	@1
	D=A
	@endFrame
	A=M-D
	D=M
	@THAT
	M=D
	@retAddr
	A=M
	0;JMP

// File Name: Class2.vm 

// function Class2.set 0 
(Class2.set)

// push argument 0 
	@0 // -- addr = LCL + 0
	D=A
	@ARG
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// pop static 0 
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@t.Class2.vm.0
	M=D

// push argument 1 
	@1 // -- addr = LCL + 1
	D=A
	@ARG
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// pop static 1 
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@t.Class2.vm.1
	M=D

// push constant 0 
	@0 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// return 

// return 
	@LCL
	D=M
	@endFrame
	M=D
	@5
	A=D-A
	D=M
	@retAddr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@ARG
	A=M
	M=D
	D=A+1
	@SP
	M=D
	@4
	D=A
	@endFrame
	A=M-D
	D=M
	@LCL
	M=D
	@3
	D=A
	@endFrame
	A=M-D
	D=M
	@ARG
	M=D
	@2
	D=A
	@endFrame
	A=M-D
	D=M
	@THIS
	M=D
	@1
	D=A
	@endFrame
	A=M-D
	D=M
	@THAT
	M=D
	@retAddr
	A=M
	0;JMP

// function Class2.get 0 
(Class2.get)

// push static 0 
	@t.Class2.vm.0 // *sp = 0
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// push static 1 
	@t.Class2.vm.1 // *sp = 1
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// sub 
	@SP
	M=M-1
	A=M
	D=M
	A=A-1
	M=D-M
	M=-M

// return 

// return 
	@LCL
	D=M
	@endFrame
	M=D
	@5
	A=D-A
	D=M
	@retAddr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@ARG
	A=M
	M=D
	D=A+1
	@SP
	M=D
	@4
	D=A
	@endFrame
	A=M-D
	D=M
	@LCL
	M=D
	@3
	D=A
	@endFrame
	A=M-D
	D=M
	@ARG
	M=D
	@2
	D=A
	@endFrame
	A=M-D
	D=M
	@THIS
	M=D
	@1
	D=A
	@endFrame
	A=M-D
	D=M
	@THAT
	M=D
	@retAddr
	A=M
	0;JMP

// File Name: Sys.vm 

// function Sys.init 0 
(Sys.init)

// push constant 6 
	@6 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// push constant 8 
	@8 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// call Class1.set 2 

// call for Class1.set 
	@t$ret.1
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@LCL
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@ARG
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THIS
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THAT
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@7
	D=A
	@SP
	D=M-D
	@ARG
	M=D
	@SP
	D=M
	@LCL
	M=D
	@Class1.set
	0;JMP
(t$ret.1)

// pop temp 0 
	@0 // addr = LCL + 0
	D=A
	@5
	D=A+D
	@addr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@addr
	A=M
	M=D

// push constant 23 
	@23 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// push constant 15 
	@15 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// call Class2.set 2 

// call for Class2.set 
	@t$ret.2
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@LCL
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@ARG
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THIS
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THAT
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@7
	D=A
	@SP
	D=M-D
	@ARG
	M=D
	@SP
	D=M
	@LCL
	M=D
	@Class2.set
	0;JMP
(t$ret.2)

// pop temp 0 
	@0 // addr = LCL + 0
	D=A
	@5
	D=A+D
	@addr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@addr
	A=M
	M=D

// call Class1.get 0 

// call for Class1.get 
	@t$ret.3
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@LCL
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@ARG
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THIS
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THAT
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@5
	D=A
	@SP
	D=M-D
	@ARG
	M=D
	@SP
	D=M
	@LCL
	M=D
	@Class1.get
	0;JMP
(t$ret.3)

// call Class2.get 0 

// call for Class2.get 
	@t$ret.4
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@LCL
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@ARG
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THIS
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THAT
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@5
	D=A
	@SP
	D=M-D
	@ARG
	M=D
	@SP
	D=M
	@LCL
	M=D
	@Class2.get
	0;JMP
(t$ret.4)

// label WHILE 
(WHILE)

// goto WHILE 
	@WHILE
	0;JMP
