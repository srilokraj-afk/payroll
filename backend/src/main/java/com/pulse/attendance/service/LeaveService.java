package com.pulse.attendance.service;

import com.pulse.attendance.dto.LeaveRequest;
import com.pulse.attendance.dto.LeaveResponse;
import com.pulse.attendance.entity.Leave;
import com.pulse.attendance.entity.Employee;
import com.pulse.attendance.enums.LeaveStatus;
import com.pulse.attendance.exception.ResourceNotFoundException;
import com.pulse.attendance.repository.LeaveRepository;
import com.pulse.attendance.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @SuppressWarnings("null")
    public LeaveResponse applyLeave(LeaveRequest request) {
        log.info("Applying leave for employeeId={}", request.getEmployeeId());

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        Leave leave = modelMapper.map(request, Leave.class);
        leave.setEmployee(employee);
        Leave savedLeave = leaveRepository.save(leave);

        return mapToResponse(savedLeave);
    }

    @SuppressWarnings("null")
    @Transactional(readOnly = true)
    public LeaveResponse getLeaveById(Long id) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found with id: " + id));
        return mapToResponse(leave);
    }

    @Transactional(readOnly = true)
    public List<LeaveResponse> getLeaveByEmployeeAndDateRange(Long employeeId, LocalDate startDate, LocalDate endDate) {
        return leaveRepository.findByEmployeeIdAndStartDateBetween(employeeId, startDate, endDate).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LeaveResponse> getLeaveByEmployeeId(Long employeeId) {
        return leaveRepository.findByEmployeeId(employeeId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LeaveResponse> getPendingLeaves() {
        return leaveRepository.findByStatus(LeaveStatus.PENDING).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LeaveResponse> getAllLeaves() {
        return leaveRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("null")
    public LeaveResponse approveLeave(Long id, String approvedBy) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found with id: " + id));
        leave.setStatus(LeaveStatus.APPROVED);
        leave.setApprovedBy(approvedBy);
        Leave updatedLeave = leaveRepository.save(leave);
        return mapToResponse(updatedLeave);
    }

    @SuppressWarnings("null")
    public LeaveResponse rejectLeave(Long id) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found with id: " + id));
        leave.setStatus(LeaveStatus.REJECTED);
        Leave updatedLeave = leaveRepository.save(leave);
        return mapToResponse(updatedLeave);
    }

    @SuppressWarnings("null")
    public void deleteLeave(Long id) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found with id: " + id));
        leaveRepository.delete(leave);
    }

    private LeaveResponse mapToResponse(Leave leave) {
        LeaveResponse response = modelMapper.map(leave, LeaveResponse.class);
        response.setEmployeeName(leave.getEmployee().getFullName());
        return response;
    }
}
