import math
x = 10
b = (int) (x * math.sqrt(8) / 4)
left = 2 * b * (b - 1)
right = x * (x - 1)
leftInc = (2 * (b + 1) * b) - left
rightInc = ((x + 1) * x) - right
def iterate():
    global left, right, leftInc, rightInc
    while (left != right):
        if (left < right):
            left += leftInc
            leftInc += 4
        elif (left > right):
            right += rightInc
            rightInc += 2
    print "*** value:", left
    right += rightInc
    rightInc += 2
for i in range(0, 5):
    iterate()
    