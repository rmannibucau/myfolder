import {Component, OnInit, ElementRef} from '@angular/core';
import {TreeService} from './tree.service';
import * as $ from 'jquery';

@Component({
  selector: 'tree',
  template: require('./tree.pug')
})
export class Tree implements OnInit {
  detail: any;

  constructor(private elt: ElementRef,
              private service: TreeService) {
  }

  ngOnInit() {
    $(this.elt.nativeElement.firstChild)
      .on('select_node.jstree', (e, args) => this.service.findDetail(args.node).subscribe(d => this.detail = d))
      .on('deselect_node.jstree', (e, node) => this.detail = undefined)
      ['jstree']({
        core : {
          data : (node, cb) => {
            this.service.loadData({folder: node.id === '#' ? undefined : node.id})
              .subscribe(d => {
                // ensure directories will lazy loaded
                d.files.filter(i => i.icon === 'directory').forEach(i => i.children = true);
                // switch icons
                d.files.forEach(i => {
                  switch(i.icon) {
                    case 'directory': i.icon = 'jstree-folder'; break;
                    case 'file': i.icon = 'jstree-file'; break;
                    default:
                  }
                })
                return cb(d.files);
              });
          }
        }
      });
    // todo: enforce to reload on open event?
  }
}
