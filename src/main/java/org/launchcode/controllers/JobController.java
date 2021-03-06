package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobDataImporter;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        model.addAttribute(jobData.findById(id));

        // TODO #1 - get the Job with the given ID and pass it into the view

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

            Job newJob = new Job();
            newJob.setName(jobForm.getName());
            newJob.setEmployer(jobData.getEmployers().findById(jobForm.getEmployerId()));
            newJob.setLocation(jobData.getLocations().findById(jobForm.getLocationId()));
            newJob.setPositionType(jobData.getPositionTypes().findById(jobForm.getPositionId()));
            newJob.setCoreCompetency(jobData.getCoreCompetencies().findById(jobForm.getSkillId()));


        if (errors.hasErrors()) {
            return "new-job";
        } else {
            model.addAttribute("newJob", newJob);
            jobData.add(newJob);

            return "redirect:/job?id=" + newJob.getId();
        }


        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.


    }
}
