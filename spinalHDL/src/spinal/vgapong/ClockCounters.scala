package vgapong

import spinal.core._
import spinal.lib._

case class ClockCounters() extends Component {

    val io = new Bundle{
        val seconds = out UInt(log2Up(60) bits)
        val minutes = out UInt(log2Up(60) bits)
        val hours   = out UInt(log2Up(12) bits)
    }

    val c_counter = Counter(0 to (25000000 - 1))
    val s_counter = Counter(0 to 59)
    val m_counter = Counter(0 to 59)
    val h_counter = Counter(0 to 11)

    c_counter.increment()

    when(c_counter.willOverflow) {
        s_counter.increment()
    }

    when(s_counter.willOverflow) {
        m_counter.increment()
    }

    when(m_counter.willOverflow) {
        h_counter.increment()
    }

    io.seconds := s_counter
    io.minutes := m_counter
    io.hours   := h_counter
}