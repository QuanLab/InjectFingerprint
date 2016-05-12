import sys
sys.path.append('./gen-py')
 
#from multi import UserAgentService
#from multi.ttypes import *
#from multi.constants import *
 
from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
 
try:
  # Make socket
  transport = TSocket.TSocket('localhost', 9090)
 
  # Buffering is critical. Raw sockets are very slow
  transport = TTransport.TBufferedTransport(transport)
 
  # Wrap in a protocol
  protocol = TBinaryProtocol.TBinaryProtocol(transport)
 
  # Create a client to use the protocol encoder
  client = UserAgentService.Client(protocol)
 
  # Connect!
  transport.open()
 
  client.multiply("Mozilla/5.0 (iPad; CPU OS 9_3_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13E238 Safari/601.1")
  print "got %s" % "message"
 
#  msg = client.sayHello()
#  print msg
#  msg = client.sayMsg(HELLO_IN_KOREAN)
#  print msg
 
  transport.close()
 
except Thrift.TException, tx:
  print "%s" % (tx.message)
