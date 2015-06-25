require 'socket'

gs = TCPServer.new 12321 #open(0)
socks = [gs]
addr = gs.addr
addr.shift
puts "Ruby server is on #{addr.join(':')}, hit ^C to terminate..."

while true
  nsock = select(socks)
  next if nsock == nil
  for s in nsock[0]
    if s == gs
      socks.push(s.accept)
      #puts "#{s} is accepted"
    else
      if s.eof?
        #puts "#{s} is gone"
        s.close
        socks.delete(s)
      else
        str = s.gets
        s.write(str.reverse!)
      end
    end
  end
end
